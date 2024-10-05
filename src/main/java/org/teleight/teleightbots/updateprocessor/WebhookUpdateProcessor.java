package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.SetWebhook;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.updateprocessor.webhook.WebhookServer;

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

        WebhookServer.getInstance().getApp().post(settings.path(), ctx -> {
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

        bot.execute(SetWebhook.ofBuilder(settings.url())
                        .certificate(settings.certificate())
                        .ipAddress(settings.ipAddress())
                        .maxConnections(settings.maxConnections())
                        .allowedUpdates(settings.allowedUpdates())
                        .dropPendingUpdates(settings.dropPendingUpdates())
                        .secretToken(settings.secretToken())
                        .build())
                .whenComplete((aBoolean, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error while setting the webhook: " + bot.getBotUsername());
                        if (bot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        bot.shutdown();
                    }
                });

        return CompletableFuture.completedFuture(null);
    }

}
