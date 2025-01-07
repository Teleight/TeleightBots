package org.teleight.teleightbots.updateprocessor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.GetMe;
import org.teleight.teleightbots.api.methods.SetWebhook;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.HttpResponseHandler;
import org.teleight.teleightbots.webhook.WebhookServer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class WebhookUpdateProcessor implements UpdateProcessor {

    private final WebhookTelegramBot bot;
    private final WebhookServer webhookServer;
    private final WebhookBotSettings settings;

    public WebhookUpdateProcessor(@NotNull WebhookTelegramBot bot, @NotNull WebhookServer webhookServer) {
        this.bot = bot;
        this.webhookServer = webhookServer;
        this.settings = bot.getBotSettings();
    }

    @Override
    public @NotNull CompletableFuture<User> start() {
        webhookServer.start();
        webhookServer.addPostRoute(settings.path(), this::handleRequest);

        return setWebhook(settings)
                .thenCompose(v -> bot.execute(new GetMe()))
                .exceptionally(throwable -> {
                    log.error("Failed to set webhook: {}", throwable.getMessage());
                    return null;
                });
    }

    private void handleRequest(HttpResponseHandler httpResponse) {
        try {
            final Update update = Update.parseResponse(httpResponse.getBody());
            if (update == null) {
                // there is no update in the request. set a response code empty response
                httpResponse.setStatusCode(HttpResponseHandler.StatusCode.NO_CONTENT);
                httpResponse.setBody("");
                return;
            }

            handleNewUpdate(bot, update, httpResponse.getBody());

            httpResponse.setStatusCode(HttpResponseHandler.StatusCode.OK);
            httpResponse.setBody(httpResponse.getBody());
        } catch (Exception e) {
            log.error("Failed to handle request: {}", e.getMessage());

            httpResponse.setStatusCode(HttpResponseHandler.StatusCode.INTERNAL_SERVER_ERROR);
            httpResponse.setBody("Internal Server Error: " + e.getMessage());
        }
    }

    private CompletableFuture<Boolean> setWebhook(@NotNull WebhookBotSettings settings) {
        return bot.execute(SetWebhook.ofBuilder(settings.url() + settings.path())
                .certificate(settings.certificate())
                .ipAddress(settings.ipAddress())
                .maxConnections(settings.maxConnections())
                .allowedUpdates(settings.allowedUpdates())
                .dropPendingUpdates(settings.dropPendingUpdates())
                .secretToken(settings.secretToken())
                .build());
    }

    @Override
    public void close() throws IOException {
        bot.execute(bot.getDeleteWebhook()).join();
        webhookServer.removePostRoute(bot.getBotSettings().path());
        webhookServer.close();
    }
}
