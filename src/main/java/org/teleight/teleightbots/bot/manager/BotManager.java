package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.bot.webhook.WebhookMessageHandler;
import org.teleight.teleightbots.updateprocessor.webhook.WebhookServerConfig;

import java.io.Closeable;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Manages the bots and their settings.
 * <p>
 * This interface provides methods to register and manage bots.
 */
public sealed interface BotManager extends Closeable permits BotManagerImpl {

    /**
     * Registers a new long polling bot with the given token and username.
     * <p>
     * The bot will be initialized with the default settings
     * </p>
     *
     * @param token            the bot's token
     * @param username         the bot's username
     * @param completeCallback the callback to be called when the bot has been registered
     */
    default void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        registerLongPolling(token, username, LongPollingBotSettings.DEFAULT, completeCallback);
    }

    /**
     * Registers a new long polling bot with the given token and username.
     * <p>
     * The bot will be initialized with custom settings provided by the given {@link LongPollingBotSettings} object.
     * </p>
     *
     * @param token               the bot's token
     * @param username            the bot's username
     * @param longPollingSettings the bot's settings
     * @param completeCallback    the callback to be called when the bot has been registered
     */
    void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback);

    default void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookMessageHandler webhookHandler) {
        registerWebhook(token, username, webhookSettings, WebhookServerConfig.DEFAULT, webhookHandler);
    }

    void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull WebhookMessageHandler webhookHandler);

    /**
     * Returns an array of all the bots managed by the BotManager.
     *
     * @return An array of all the bots managed by the BotManager.
     */
    @NotNull @Unmodifiable Collection<TelegramBot> getRegisteredBots();

}
