package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.SetWebhook;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.updateprocessor.webhook.WebhookServer;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class WebhookUpdateProcessor implements UpdateProcessor {

    private final WebhookTelegramBot bot;

    public WebhookUpdateProcessor(@NotNull WebhookTelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public @NotNull CompletableFuture<User> start() {
        var settings = bot.getBotSettings();
        if (settings.path() == null) {
            settings = settings.toBuilder().path("/" + bot.getBotUsername()).build();
        }

        WebhookServer.getInstance().addPostRoute(settings.path(), ctx -> {
            try {
                Update update = ctx.bodyStreamAsClass(Update.class);
                Optional<ApiMethod<?>> response = Optional.ofNullable(bot.getWebhookBotInfo().consumeUpdate(update));
                response.ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(204) // No content
                );
            } catch (Exception e) {
                ctx.status(500).json("Internal Server Error: " + e.getMessage());
            }
        });

        setWebhook(settings);

        return CompletableFuture.completedFuture(null);
    }

    private void setWebhook(@NotNull WebhookBotSettings settings) {
        final var webHookResult = bot.execute(SetWebhook.ofBuilder(settings.url())
                .certificate(settings.certificate())
                .ipAddress(settings.ipAddress())
                .maxConnections(settings.maxConnections())
                .allowedUpdates(settings.allowedUpdates())
                .dropPendingUpdates(settings.dropPendingUpdates())
                .secretToken(settings.secretToken())
                .build());

        webHookResult.whenComplete((success, throwable) -> {
            if (throwable != null || !success) {
                System.out.println("Error while setting the webhook: " + bot.getBotUsername());
                if (bot.getBotSettings().silentlyThrowMethodExecution() && throwable != null) {
                    TeleightBots.getExceptionManager().handleException(throwable);
                }
                WebhookServer.getInstance().removePostRoute(settings.path());
                bot.shutdown();
            }
        });
    }

    @Override
    public void close() throws IOException {
        WebhookServer.getInstance().removePostRoute(bot.getBotSettings().path());
    }
}
