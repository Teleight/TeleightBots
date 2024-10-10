package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.methods.GetMe;
import org.teleight.teleightbots.api.methods.SetWebhook;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.WebhookServer;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.teleight.teleightbots.api.ApiMethod.OBJECT_MAPPER;

public final class WebhookUpdateProcessor implements UpdateProcessor {

    private final WebhookTelegramBot bot;
    private final WebhookServer webhookServer;
    private final WebhookBotSettings settings;

    public WebhookUpdateProcessor(@NotNull WebhookTelegramBot bot, @NotNull WebhookServer webhookServer) {
        this.bot = bot;
        this.webhookServer = webhookServer;

        WebhookBotSettings webhookBotSettings = bot.getBotSettings();
        if (webhookBotSettings.path() == null) {
            webhookBotSettings = webhookBotSettings.toBuilder().path("/" + bot.getBotUsername()).build();
        }
        this.settings = webhookBotSettings;
    }

    @Override
    public @NotNull CompletableFuture<User> start() {
        webhookServer.addPostRoute(settings.path(), ctx -> {
            try {
                final Update update = OBJECT_MAPPER.readValue(ctx.body(), Update.class);
                final Optional<? extends CompletableFuture<?>> response = Optional.of(handleNewUpdate(bot, update, ctx.body()));
                response.ifPresentOrElse(
                        ctx::json,
                        () -> ctx.status(204) // No content
                );
            } catch (Exception e) {
                ctx.status(500).json("Internal Server Error: " + e.getMessage());
            }
        });

        setWebhook(settings);

        return bot.execute(new GetMe())
                .whenComplete((user, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error while authenticating the bot: " + bot.getBotUsername());
                        if (bot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        webhookServer.removePostRoute(settings.path());
                        bot.shutdown();
                        return;
                    }
                    System.out.println("Bot authenticated: " + user.username());
                });
    }

    private void setWebhook(@NotNull WebhookBotSettings settings) {
        final CompletableFuture<Boolean> webHookResult = bot.execute(SetWebhook.ofBuilder(settings.url())
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
                webhookServer.removePostRoute(settings.path());
                bot.shutdown();
            }
            System.out.println("Webhook authenticated: " + success);
        });
    }

    @Override
    public void close() throws IOException {
        webhookServer.removePostRoute(bot.getBotSettings().path());
    }
}
