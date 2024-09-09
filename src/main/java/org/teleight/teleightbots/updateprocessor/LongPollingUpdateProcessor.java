package org.teleight.teleightbots.updateprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.methods.GetMe;
import org.teleight.teleightbots.api.methods.GetUpdates;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.InputMedia;
import org.teleight.teleightbots.api.objects.InputMediaAnimation;
import org.teleight.teleightbots.api.objects.InputMediaAudio;
import org.teleight.teleightbots.api.objects.InputMediaDocument;
import org.teleight.teleightbots.api.objects.InputMediaPhoto;
import org.teleight.teleightbots.api.objects.InputMediaVideo;
import org.teleight.teleightbots.api.objects.InputPaidMedia;
import org.teleight.teleightbots.api.objects.InputPaidMediaPhoto;
import org.teleight.teleightbots.api.objects.InputPaidMediaVideo;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.settings.BotSettings;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.exception.exceptions.RateLimitException;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.updateprocessor.events.CallbackQueryEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.ChannelPostEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.ChatMemberStatusEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.EventProcessor;
import org.teleight.teleightbots.updateprocessor.events.InlineQueryEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.MessageEventProcessor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static org.teleight.teleightbots.api.ApiMethod.OBJECT_MAPPER;

public class LongPollingUpdateProcessor implements UpdateProcessor {

    private final CountDownLatch processorLatch = new CountDownLatch(1);

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(75, ChronoUnit.SECONDS))
            .version(HttpClient.Version.HTTP_2)
            .build();
    private Thread updateProcessorThread;
    private TelegramBot bot;
    private int lastReceivedUpdate = 0;

    @Override
    public void setBot(@NotNull TelegramBot bot) {
        if (this.bot != null) {
            throw new IllegalArgumentException("Bot instance was already assigned to this update processor");
        }
        this.bot = bot;
    }

    @Override
    public CompletableFuture<User> start() {
        updateProcessorThread = new UpdateProcessorThread();
        updateProcessorThread.setName(bot.getBotUsername() + " Update Processor");
        updateProcessorThread.start();

        return bot.execute(new GetMe())
                .whenComplete((user, throwable) -> {
                    if (throwable != null) {
                        return;
                    }
                    System.out.println("Bot authenticated: " + user.username());
                    processorLatch.countDown();
                });
    }

    @Override
    public void close() {
        updateProcessorThread.interrupt();
    }

    private void executeGetUpdates() {
        final BotSettings settings = bot.getBotSettings();

        final GetUpdates getUpdates = GetUpdates.ofBuilder()
                .timeout(settings.updatesTimeout())
                .limit(settings.updatesLimit())
                .offset(lastReceivedUpdate + 1)
                .build();

        final String responseJson = UNSAFE_executeMethod(getUpdates)
                .exceptionally(throwable -> {
                    TeleightBots.getExceptionManager().handleException(throwable);
                    return null;
                })
                .join();
        if(responseJson == null){
            return;
        }

        Update[] updates;
        try {
            updates = getUpdates.deserializeResponse(responseJson);
        } catch (TelegramRequestException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return;
        }

        if (updates.length == 0) {
            return;
        }

        handleUpdates(updates, responseJson);
    }

    private void handleUpdates(@NotNull Update[] updates, @NotNull String responseJson) {
        // Mark updates for removal
        int newSize = 0;
        for (int i = 0; i < updates.length; i++) {
            Update update = updates[i];
            if (update.updateId() >= lastReceivedUpdate) {
                updates[newSize] = update;
                newSize++;
            }
        }

        // Compact the array
        if (newSize < updates.length) {
            Update[] newUpdates = new Update[newSize];
            System.arraycopy(updates, 0, newUpdates, 0, newSize);
            updates = newUpdates;
        }

        // Process the updates
        for (final Update update : updates) {
            handleNewUpdate(update, responseJson);
        }

        // Update lastReceivedUpdate
        if (updates.length > 0) {
            lastReceivedUpdate = updates[updates.length - 1].updateId();
        }
    }

    private final EventProcessor[] processorEvents = new EventProcessor[] {
            new CallbackQueryEventProcessor(),
            new ChannelPostEventProcessor(),
            new InlineQueryEventProcessor(),
            new MessageEventProcessor(),
            new ChatMemberStatusEventProcessor()
    };

    @ApiStatus.Internal
    private void handleNewUpdate(@NotNull Update update, String responseJson) {
        bot.getEventManager()
                .call(new UpdateReceivedEvent(bot, update, responseJson))
                .thenAccept(updateReceivedEvent -> {
                    final Update receivedUpdate = updateReceivedEvent.update();
                    for (EventProcessor processorEvent : processorEvents) {
                        processorEvent.processUpdate(bot, receivedUpdate);
                    }
                });
    }

    @Override
    public @NotNull CompletableFuture<String> executeMethod(@NotNull ApiMethod<?> method) {
        return UNSAFE_executeMethod(method);
    }

    @ApiStatus.Internal
    public @NotNull CompletableFuture<String> UNSAFE_executeMethod(@NotNull ApiMethod<?> method) {
        //Request
        final String botToken = bot.getBotToken();
        final BotSettings settings = bot.getBotSettings();
        final String url = settings.endpointUrl() + botToken + "/" + method.getEndpointURL();

        final CompletableFuture<String> requestFuture = new CompletableFuture<>();

        Thread.ofVirtual().name(bot.getBotUsername() + " LongPolling Request Dispatcher").start(() -> {
            try {
                final HttpRequest request;
                try {
                    request = createRequest(method, url);
                } catch (JsonProcessingException e) {
                    requestFuture.completeExceptionally(e);
                    return;
                }
                final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final int statusCode = response.statusCode();
                final String responseBody = response.body();

                if (statusCode == 429) {
                    //Rate limit reached
                    requestFuture.completeExceptionally(new RateLimitException());
                    return;
                }

                requestFuture.complete(responseBody);
            } catch (IOException e) {
                requestFuture.completeExceptionally(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                requestFuture.completeExceptionally(e);
            }
        });
        return requestFuture;
    }

    @ApiStatus.Internal
    private HttpRequest createRequest(ApiMethod<?> method, String url) throws JsonProcessingException {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.of(75,ChronoUnit.SECONDS));
        final HttpRequest.BodyPublisher body;

        if (method instanceof MultiPartApiMethod<?> multiPartApiMethod) {
            final MultiPartBodyPublisher publisher = new MultiPartBodyPublisher();
            for (Map.Entry<String, Object> stringObjectEntry : multiPartApiMethod.getParameters().entrySet()) {
                final String key = stringObjectEntry.getKey();
                final Object value = stringObjectEntry.getValue();

                if (value == null) {
                    continue;
                }

                switch (value) {
                    case String string -> publisher.addPart(key, string);
                    case InputFile inputFile -> {
                        if (inputFile.id() != null) {
                            publisher.addPart(key, inputFile.id());
                        } else {
                            publisher.addPart(key, inputFile.file(), inputFile.fileName());
                        }
                    }
                    case InputMedia inputMedia -> {
                        handleInputMedia(publisher, inputMedia);
                        publisher.addPart(key, OBJECT_MAPPER.writeValueAsString(inputMedia));
                    }
                    case InputPaidMedia[] inputPaidMedias -> {
                        handleInputPaidMedias(publisher, inputPaidMedias);
                        publisher.addPart(key, OBJECT_MAPPER.writeValueAsString(inputPaidMedias));
                    }
                    default -> publisher.addPart(key, OBJECT_MAPPER.writeValueAsString(value));
                }
            }
            body = publisher.build();
            requestBuilder.header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
        } else {
            final String jsonString = OBJECT_MAPPER.writeValueAsString(method);
            body = HttpRequest.BodyPublishers.ofString(jsonString);

            requestBuilder.header("Content-Type", "application/json");
        }

        return requestBuilder.POST(body).build();
    }

    private void addMultiMediaPart(MultiPartBodyPublisher publisher, InputFile inputFileMedia, InputFile inputFileThumbnail) {
        if (inputFileMedia.id() != null) {
            publisher.addPart(inputFileMedia.fileName(), inputFileMedia.id());
        } else {
            publisher.addPart(inputFileMedia.fileName(), inputFileMedia.file(), inputFileMedia.fileName());
        }
        if (inputFileThumbnail != null) {
            if (inputFileThumbnail.id() != null) {
                publisher.addPart(inputFileThumbnail.fileName(), inputFileThumbnail.id());
            } else {
                publisher.addPart(inputFileThumbnail.fileName(), inputFileThumbnail.file(), inputFileThumbnail.fileName());
            }
        }
    }

    private void handleInputMedia(MultiPartBodyPublisher publisher, InputMedia inputMedia) {
        switch (inputMedia) {
            case InputMediaPhoto photo -> addMultiMediaPart(publisher, photo.media(), null);
            case InputMediaDocument document -> addMultiMediaPart(publisher, document.media(), document.thumbnail());
            case InputMediaAudio audio -> addMultiMediaPart(publisher, audio.media(), audio.thumbnail());
            case InputMediaAnimation animation -> addMultiMediaPart(publisher, animation.media(), animation.thumbnail());
            case InputMediaVideo video -> addMultiMediaPart(publisher, video.media(), video.thumbnail());
            default -> throw new IllegalStateException("Unexpected value: " + inputMedia);
        }
    }

    private void handleInputPaidMedias(MultiPartBodyPublisher publisher, InputPaidMedia[] inputPaidMedias) {
        for (InputPaidMedia inputPaidMedia : inputPaidMedias) {
            switch (inputPaidMedia) {
                case InputPaidMediaPhoto photo -> addMultiMediaPart(publisher, photo.media(), null);
                case InputPaidMediaVideo video -> addMultiMediaPart(publisher, video.media(), video.thumbnail());
                default -> throw new IllegalStateException("Unexpected value: " + inputPaidMedia);
            }
        }
    }

    private class UpdateProcessorThread extends Thread {
        @Override
        public void run() {
            try {
                processorLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            while (!Thread.currentThread().isInterrupted()) {
                executeGetUpdates();
            }
        }
    }

}
