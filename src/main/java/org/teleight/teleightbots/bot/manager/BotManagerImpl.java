package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.bot.webhook.WebhookMessageHandler;
import org.teleight.teleightbots.updateprocessor.webhook.WebhookServer;
import org.teleight.teleightbots.updateprocessor.webhook.WebhookServerConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class BotManagerImpl implements BotManager {

    private final List<TelegramBot> registeredBots = new CopyOnWriteArrayList<>();

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        final var bot = new LongPollingTelegramBot(token, username, longPollingSettings);
        final var updateProcessor = bot.getUpdateProcessor();
        updateProcessor.start().thenRun(() -> {
            if (longPollingSettings.extensionsEnabled()) {
                bot.getExtensionManager().start();
            }

            registeredBots.add(bot);
            try {
                completeCallback.accept(bot);
            } catch (Throwable t) {
                TeleightBots.getExceptionManager().handleException(t);
            }
        });
    }

    @Override
    public void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull WebhookMessageHandler webhookHandler) {
        username = sanitizeUsername(username);

        final var bot = new WebhookTelegramBot(token, username, webhookSettings, webhookHandler);
        WebhookServer.getInstance().start(serverConfig);
        bot.getUpdateProcessor().start().thenRun(() -> {
            if (webhookSettings.extensionsEnabled()) {
                bot.getExtensionManager().start();
            }

            registeredBots.add(bot);
        });
    }

    @Override
    public @NotNull @Unmodifiable Collection<TelegramBot> getRegisteredBots() {
        return registeredBots;
    }

    @Override
    public void close() throws IOException {
        for (final TelegramBot registeredBot : registeredBots) {
            registeredBot.shutdown();
        }
        registeredBots.clear();
    }

    private String sanitizeUsername(String username) {
        // If the bot username starts with a @, then we trim it.
        // Telegram bot usernames must not contain the at symbol
        if (username.startsWith("@")) {
            username = username.substring(1);
        }
        return username;
    }

}
