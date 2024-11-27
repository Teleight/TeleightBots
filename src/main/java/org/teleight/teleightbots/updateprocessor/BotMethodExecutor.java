package org.teleight.teleightbots.updateprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.MultiPartApiMethod;
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
import org.teleight.teleightbots.api.objects.InputSticker;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.settings.BotSettings;
import org.teleight.teleightbots.exception.exceptions.RateLimitException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.teleight.teleightbots.api.ApiMethod.OBJECT_MAPPER;

@ApiStatus.Internal
public final class BotMethodExecutor {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(75, ChronoUnit.SECONDS))
            .version(HttpClient.Version.HTTP_2)
            .build();

    private final TelegramBot bot;

    public BotMethodExecutor(TelegramBot bot) {
        this.bot = bot;
    }

    @ApiStatus.Internal
    public @NotNull CompletableFuture<String> executeMethod(@NotNull ApiMethod<?> method) {
        //Request
        final String botToken = bot.getBotToken();
        final BotSettings settings = bot.getBotSettings();
        final String url = settings.endpointUrl() + botToken + "/" + method.getEndpointURL();

        final CompletableFuture<String> requestFuture = new CompletableFuture<>();

        Thread.ofVirtual().name(bot.getBotUsername() + " Request Dispatcher").start(() -> {
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
                .timeout(Duration.of(75, ChronoUnit.SECONDS));
        final HttpRequest.BodyPublisher body;

        if (method instanceof MultiPartApiMethod<?> multiPartApiMethod) {
            final MultiPartBodyPublisher publisher = new MultiPartBodyPublisher();

            handleInputFields(publisher, multiPartApiMethod.getParameters());

            body = publisher.build();
            requestBuilder.header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
        } else {
            final String jsonString = OBJECT_MAPPER.writeValueAsString(method);
            body = HttpRequest.BodyPublishers.ofString(jsonString);

            requestBuilder.header("Content-Type", "application/json");
        }

        return requestBuilder.POST(body).build();
    }

    @ApiStatus.Internal
    private void handleInputFields(@NotNull MultiPartBodyPublisher publisher,
                                   @NotNull Map<String, Object> params)
            throws JsonProcessingException {
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
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
                case InputSticker[] inputStickers -> {
                    for (InputSticker inputSticker : inputStickers) {
                        addMultiMediaPart(publisher, inputSticker.sticker(), null);
                    }
                    publisher.addPart(key, OBJECT_MAPPER.writeValueAsString(inputStickers));
                }
                default -> publisher.addPart(key, OBJECT_MAPPER.writeValueAsString(value));
            }
        }
    }

    @ApiStatus.Internal
    private void addMultiMediaPart(@NotNull MultiPartBodyPublisher publisher,
                                   @NotNull InputFile inputFileMedia,
                                   @Nullable InputFile inputFileThumbnail) {
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

    @ApiStatus.Internal
    private void handleInputMedia(@NotNull MultiPartBodyPublisher publisher,
                                  @NotNull InputMedia inputMedia) {
        switch (inputMedia) {
            case InputMediaPhoto photo -> addMultiMediaPart(publisher, photo.media(), null);
            case InputMediaDocument document -> addMultiMediaPart(publisher, document.media(), document.thumbnail());
            case InputMediaAudio audio -> addMultiMediaPart(publisher, audio.media(), audio.thumbnail());
            case InputMediaAnimation animation ->
                    addMultiMediaPart(publisher, animation.media(), animation.thumbnail());
            case InputMediaVideo video -> addMultiMediaPart(publisher, video.media(), video.thumbnail());
            default -> throw new IllegalStateException("Unexpected value: " + inputMedia);
        }
    }

    @ApiStatus.Internal
    private void handleInputPaidMedias(@NotNull MultiPartBodyPublisher publisher,
                                       @NotNull InputPaidMedia[] inputPaidMedias) {
        for (InputPaidMedia inputPaidMedia : inputPaidMedias) {
            switch (inputPaidMedia) {
                case InputPaidMediaPhoto photo -> addMultiMediaPart(publisher, photo.media(), null);
                case InputPaidMediaVideo video -> addMultiMediaPart(publisher, video.media(), video.thumbnail());
                default -> throw new IllegalStateException("Unexpected value: " + inputPaidMedia);
            }
        }
    }

}
