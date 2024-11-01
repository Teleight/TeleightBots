package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
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
        webhookServer.start();
        setupWebhookRoute();

        return setWebhook(settings).whenComplete((success, throwable) -> {
            if (throwable != null) {
                System.out.println("Failed to set webhook: " + throwable.getMessage());
            }
        }).thenCompose(aBoolean -> bot.execute(new GetMe()));
    }

    private void setupWebhookRoute() {
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
    }

    private CompletableFuture<Boolean> setWebhook(@NotNull WebhookBotSettings settings) {
        return bot.execute(SetWebhook.ofBuilder(settings.url())
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
        webhookServer.removePostRoute(bot.getBotSettings().path());
    }
}
