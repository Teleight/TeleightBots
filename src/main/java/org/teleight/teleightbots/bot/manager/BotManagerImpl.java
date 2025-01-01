package org.teleight.teleightbots.bot.manager;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.event.bot.BotShutdownEvent;
import org.teleight.teleightbots.webhook.WebhookServer;
import org.teleight.teleightbots.webhook.WebhookServerConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Slf4j
final class BotManagerImpl implements BotManager {

    private static boolean created = false;

    private final List<TelegramBot> registeredBots = new CopyOnWriteArrayList<>();
    private final Map<WebhookServerConfig, WebhookServer> registeredInternalWebhookServers = new ConcurrentHashMap<>();

    BotManagerImpl() {
        if (created) {
            throw new IllegalStateException("There can only be one instance of BotManager");
        }
        created = true;
    }

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        final LongPollingTelegramBot bot = LongPollingTelegramBot.create(token, username, longPollingSettings);
        startProcessor(bot, completeCallback);
    }

    @Override
    public void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServer webhookServer, @NotNull Consumer<WebhookTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        final WebhookTelegramBot bot = WebhookTelegramBot.create(token, username, webhookSettings, webhookServer);
        startProcessor(bot, completeCallback);
    }

    @Override
    public void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull Consumer<WebhookTelegramBot> completeCallback) {
        username = sanitizeUsername(username);

        // If the server is already registered, we use the existing server.
        // Otherwise, we create a new one.
        // But we don't start the server here, we start it when the bot is started.
        final WebhookServer webhookServer = registeredInternalWebhookServers.computeIfAbsent(serverConfig, WebhookServer::internal);

        final WebhookTelegramBot bot = WebhookTelegramBot.create(token, username, webhookSettings, webhookServer);
        startProcessor(bot, completeCallback);
    }

    private <T extends TelegramBot> void startProcessor(@NotNull T telegramBot, @NotNull Consumer<T> completeCallback) {
        log.info("Authenticating bot {}", telegramBot.getBotUsername());

        telegramBot.getUpdateProcessor().start()
                .whenComplete((botUser, throwable) -> {
                    // If an error occurred while authenticating the bot, we log the error and shutdown the bot.
                    // Happens when the bot token or the username is invalid.
                    // It can also happen if the webhook was considered invalid by the bot API.
                    if (throwable != null) {
                        log.error("An error occurred while authenticating the bot {}: {}", telegramBot.getBotUsername(), throwable.getMessage());
                        if (!telegramBot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        shutdownBot(telegramBot);
                        return;
                    }
                    log.info("Bot {} authenticated successfully", telegramBot.getBotUsername());
                    if (telegramBot.getBotSettings().extensionsEnabled()) {
                        telegramBot.getExtensionManager().start();
                    }
                    registeredBots.add(telegramBot);
                    completeCallback.accept(telegramBot);
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
                shutdownBot(registeredBot);
                registeredBots.remove(registeredBot);
                return;
            }
        }
    }

    @Override
    public void unregisterBot(@NotNull TelegramBot bot) {
        if (registeredBots.contains(bot)) {
            shutdownBot(bot);
            registeredBots.remove(bot);
        }
    }

    private void shutdownBot(@NotNull TelegramBot bot) {
        bot.getEventManager().call(new BotShutdownEvent(bot));
        try {
            if (bot.getBotSettings().extensionsEnabled()) {
                bot.getExtensionManager().close();
            }
            bot.getScheduler().close();
            bot.getUpdateProcessor().close();
            bot.getFileDownloader().close();
        } catch (Exception e) {
            TeleightBots.getExceptionManager().handleException(e);
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
