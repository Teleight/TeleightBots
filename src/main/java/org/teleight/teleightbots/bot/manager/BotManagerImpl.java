package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.WebhookServer;
import org.teleight.teleightbots.webhook.WebhookServerConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class BotManagerImpl implements BotManager {

    private final List<TelegramBot> registeredBots = new CopyOnWriteArrayList<>();
    private final Map<WebhookServerConfig, WebhookServer> registeredWebhookServers = new ConcurrentHashMap<>();

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        final var bot = LongPollingTelegramBot.create(token, username, longPollingSettings);
        startProcessor(bot, completeCallback);
    }

    @Override
    public void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull Consumer<WebhookTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        // If the server is already registered, we use the existing server.
        // Otherwise, we create a new one.
        // But we don't start the server here, we start it when the bot is started.
        final var webhookServer = registeredWebhookServers.computeIfAbsent(serverConfig, WebhookServer::create);

        final var bot = WebhookTelegramBot.create(token, username, webhookSettings, webhookServer);

        startProcessor(bot, completeCallback);
    }

    private <T extends TelegramBot> void startProcessor(@NotNull T telegramBot, @NotNull Consumer<T> completeCallback) {
        telegramBot.getUpdateProcessor().start().thenRun(() -> {
            if (telegramBot.getBotSettings().extensionsEnabled()) {
                telegramBot.getExtensionManager().start();
            }

            registeredBots.add(telegramBot);
            try {
                completeCallback.accept(telegramBot);
            } catch (Throwable t) {
                TeleightBots.getExceptionManager().handleException(t);
            }
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
