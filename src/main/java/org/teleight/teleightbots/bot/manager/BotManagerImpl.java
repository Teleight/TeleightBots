package org.teleight.teleightbots.bot.manager;

import org.checkerframework.com.google.common.base.Preconditions;
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

final class BotManagerImpl implements BotManager {

    private static boolean created = false;

    private final List<TelegramBot> registeredBots = new CopyOnWriteArrayList<>();
    private final Map<WebhookServerConfig, WebhookServer> registeredWebhookServers = new ConcurrentHashMap<>();

    BotManagerImpl() {
        Preconditions.checkState(!created, "There can only be one instance of BotManager");
        created = true;
    }

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        final LongPollingTelegramBot bot = LongPollingTelegramBot.create(token, username, longPollingSettings);
        startProcessor(bot, completeCallback);
    }

    @Override
    public void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull Consumer<WebhookTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        // If the server is already registered, we use the existing server.
        // Otherwise, we create a new one.
        // But we don't start the server here, we start it when the bot is started.
        final WebhookServer webhookServer = registeredWebhookServers.computeIfAbsent(serverConfig, WebhookServer::create);

        final WebhookTelegramBot bot = WebhookTelegramBot.create(token, username, webhookSettings, webhookServer);

        startProcessor(bot, completeCallback);
    }

    private <T extends TelegramBot> void startProcessor(@NotNull T telegramBot, @NotNull Consumer<T> completeCallback) {
        System.out.println("Authenticating bot " + telegramBot.getBotUsername());

        telegramBot.getUpdateProcessor().start()
                .thenRun(() -> {
                    if (telegramBot.getBotSettings().extensionsEnabled()) {
                        telegramBot.getExtensionManager().start();
                    }
                    registeredBots.add(telegramBot);
                    completeCallback.accept(telegramBot);
                })
                .exceptionally(throwable -> {
                    // If an error occurred while authenticating the bot, we log the error and shutdown the bot.
                    // Happens when the bot token or the username is invalid.
                    // It can also happen if the webhook was considered invalid by the bot API.
                    if (throwable != null) {
                        System.out.println("An error occurred while authenticating the bot " + telegramBot.getBotUsername() + ": " + throwable.getMessage());
                        if (!telegramBot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        telegramBot.shutdown();
                        return null;
                    }
                    return null;
                });
    }

    @Override
    public @NotNull @Unmodifiable Collection<TelegramBot> getRegisteredBots() {
        return registeredBots;
    }

    @Override
    public void unregisterBot(@NotNull String botToken) {
        for (final TelegramBot registeredBot : registeredBots) {
            if (registeredBot.getBotToken().equals(botToken)) {
                registeredBot.shutdown();
                registeredBots.remove(registeredBot);
                return;
            }
        }
    }

    @Override
    public void unregisterBot(@NotNull TelegramBot bot) {
        if (registeredBots.contains(bot)) {
            bot.shutdown();
            registeredBots.remove(bot);
        }
    }

    @Override
    public void close() throws IOException {
        for (final TelegramBot telegramBot : registeredBots) {
            unregisterBot(telegramBot);
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
