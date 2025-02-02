package org.teleight.teleightbots.updateprocessor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.GetMe;
import org.teleight.teleightbots.api.methods.SetWebhook;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.HttpResponse;
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
        try {
            webhookServer.start();
            webhookServer.addPostRoute(settings.path(), this::handleRequest);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }

        return setWebhook(settings)
                .thenCompose(v -> bot.execute(new GetMe()))
                .exceptionally(throwable -> {
                    log.error("Failed to set webhook: {}", throwable.getMessage());
                    return null;
                });
    }

    private HttpResponse handleRequest(String body) {
        try {
            final Update update = Update.parseResponse(body);
            if (update == null) {
                // there is no update in the request. set a response code empty response
                return HttpResponse.noContent();
            }
            handleNewUpdate(bot, update, body);
            return HttpResponse.ok(body);
        } catch (Exception e) {
            log.error("Failed to handle request: {}", e.getMessage());
            return HttpResponse.error("Internal Server Error: " + e.getMessage());
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
        if (webhookServer.isRunning()) {
            try {
                webhookServer.removePostRoute(settings.path());
                webhookServer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
