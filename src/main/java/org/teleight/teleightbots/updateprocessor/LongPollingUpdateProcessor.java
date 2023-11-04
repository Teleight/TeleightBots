package org.teleight.teleightbots.updateprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.GetUpdates;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;
import org.teleight.teleightbots.exception.exceptions.RateLimitException;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class LongPollingUpdateProcessor implements UpdateProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(60, ChronoUnit.SECONDS))
            .build();
    private final AtomicBoolean credentialsCorrect = new AtomicBoolean();
    private Thread updateProcessorThread;
    private Bot bot;
    private int lastReceivedUpdate = 0;

    @Override
    public void setBot(@NotNull Bot bot) {
        if (this.bot != null) {
            throw new IllegalArgumentException("Bot instant was already assigned to this update processor");
        }
        this.bot = bot;
    }

    @Override
    public void start() {
        updateProcessorThread = new Thread(new UpdateProcessorThread());
        updateProcessorThread.setName(bot.getBotUsername() + " Update Processor");
        updateProcessorThread.start();
    }

    public void terminate() {
        updateProcessorThread.interrupt();
    }

    public @NotNull AtomicBoolean getCredentialsCorrect() {
        return credentialsCorrect;
    }

    private void executeGetUpdates() {
        final BotSettings settings = bot.getBotSettings();

        final GetUpdates getUpdates = GetUpdates.of()
                .withTimeout(settings.updatesTimeout())
                .withLimit(settings.updatesLimit())
                .withOffset(lastReceivedUpdate + 1);

        final String responseJson = UNSAFE_executeMethod(getUpdates).join();
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
        final Update[] finalUpdates = updates;
        bot.getScheduler().buildTask(() -> {
            for (final Update update : finalUpdates) {
                handleNewUpdate(update);
            }
        }).schedule();

        // Update lastReceivedUpdate
        if (updates.length > 0) {
            lastReceivedUpdate = updates[updates.length - 1].updateId();
        }
    }

    @ApiStatus.Internal
    private void handleNewUpdate(@NotNull Update update) {
        bot.getEventManager()
                .call(new UpdateReceivedEvent(bot, update))
                .thenRun(() -> {
                    final boolean hasCallbackQuery = update.callbackQuery() != null;
                    if (hasCallbackQuery) {
                        final ButtonPressEvent buttonPressEvent = new ButtonPressEvent(bot, update.callbackQuery());
                        buttonPressEvent.completeCallback();
                        bot.getPaginationManager().getEventNode().call(buttonPressEvent);
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
                final HttpRequest.BodyPublisher body;
                try {
                    body = createRequestBody(method);
                } catch (JsonProcessingException e) {
                    requestFuture.completeExceptionally(e);
                    return;
                }

                final HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(body)
                        .build();

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
    private HttpRequest.BodyPublisher createRequestBody(ApiMethod<?> method) throws JsonProcessingException {
        final String jsonString = objectMapper.writeValueAsString(method);
        return HttpRequest.BodyPublishers.ofString(jsonString);
    }

    private class UpdateProcessorThread implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (credentialsCorrect.get()) {
                    executeGetUpdates();
                }
            }
        }
    }

}
