package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.WebhookServerConfig;

import java.io.Closeable;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Interface for managing bots and their settings.
 * <p>
 * The {@code BotManager} provides methods to register and manage bots, supporting both
 * long polling and webhook-based Telegram bots. It also allows for retrieving the bots
 * that are currently registered with the manager.
 * </p>
 */
public sealed interface BotManager extends Closeable permits BotManagerImpl {

    @ApiStatus.Internal
    static @NotNull BotManager newBotManager() {
        return new BotManagerImpl();
    }

    /**
     * Registers a new long polling bot with the default settings.
     * <p>
     * This method simplifies the bot registration process by using default settings provided
     * by {@link LongPollingBotSettings#DEFAULT}. After the bot is successfully registered,
     * the provided {@code completeCallback} will be invoked.
     * </p>
     *
     * @param token            the bot's token, required for authentication with Telegram
     * @param username         the bot's username, as it appears in Telegram
     * @param completeCallback the callback that will be invoked when the bot has been registered
     * @throws NullPointerException if any of the arguments are {@code null}
     * @see #registerLongPolling(String, String, LongPollingBotSettings, Consumer)
     */
    default void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull Consumer<LongPollingTelegramBot> completeCallback) {
        registerLongPolling(token, username, LongPollingBotSettings.DEFAULT, completeCallback);
    }

    /**
     * Registers a new long polling bot with custom settings.
     * <p>
     * This method allows for fine-grained control over the bot's behavior by accepting
     * a {@link LongPollingBotSettings} object. The bot is registered with these settings
     * and initialized accordingly. Upon successful registration, the {@code completeCallback}
     * will be invoked.
     * </p>
     *
     * @param token               the bot's token, required for authentication with Telegram
     * @param username            the bot's username, as it appears in Telegram
     * @param longPollingSettings the settings to configure the long polling bot
     * @param completeCallback    the callback that will be invoked when the bot has been registered
     * @throws NullPointerException if any of the arguments are {@code null}
     */
    void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull LongPollingBotSettings longPollingSettings, @NotNull Consumer<LongPollingTelegramBot> completeCallback);

    /**
     * Registers a new webhook-based bot with the default server configuration.
     * <p>
     * This method registers a bot that communicates with Telegram using webhooks.
     * It uses default server configuration provided by {@link WebhookServerConfig#DEFAULT}.
     * Once the bot is registered, the provided {@code completeCallback} will be invoked.
     * </p>
     *
     * @param token            the bot's token, required for authentication with Telegram
     * @param username         the bot's username, as it appears in Telegram
     * @param webhookSettings  the settings to configure the webhook bot
     * @param completeCallback the callback that will be invoked when the bot has been registered
     * @throws NullPointerException if any of the arguments are {@code null}
     * @see #registerWebhook(String, String, WebhookBotSettings, WebhookServerConfig, Consumer)
     */
    default void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull Consumer<WebhookTelegramBot> completeCallback) {
        registerWebhook(token, username, webhookSettings, WebhookServerConfig.DEFAULT, completeCallback);
    }

    /**
     * Registers a new webhook-based bot with custom server configuration.
     * <p>
     * This method allows configuring both the bot settings and the server configuration
     * for a webhook-based bot. Once registered, the {@code completeCallback} will be invoked.
     * </p>
     *
     * @param token            the bot's token, required for authentication with Telegram
     * @param username         the bot's username, as it appears in Telegram
     * @param webhookSettings  the settings to configure the webhook bot
     * @param serverConfig     the server configuration for handling webhook requests
     * @param completeCallback the callback that will be invoked when the bot has been registered
     * @throws NullPointerException if any of the arguments are {@code null}
     */
    void registerWebhook(@NotNull String token, @NotNull String username, @NotNull WebhookBotSettings webhookSettings, @NotNull WebhookServerConfig serverConfig, @NotNull Consumer<WebhookTelegramBot> completeCallback);

    /**
     * Unregisters a bot from the manager.
     * <p>
     * This method removes the specified bot from the manager, effectively stopping
     * the bot and cleaning up any resources associated with it.
     * </p>
     *
     * @param botToken the bot (token) to unregister
     * @throws NullPointerException if the bot is {@code null}
     */
    void unregisterBot(@NotNull String botToken);

    /**
     * Unregisters a bot from the manager.
     * <p>
     * This method removes the specified bot from the manager, effectively stopping
     * the bot and cleaning up any resources associated with it.
     * </p>
     *
     * @param telegramBot the bot to unregister
     * @throws NullPointerException if the bot is {@code null}
     */
    void unregisterBot(@NotNull TelegramBot telegramBot);

    /**
     * Retrieves all the bots currently managed by the {@code BotManager}.
     * <p>
     * This method returns an unmodifiable collection of all the bots registered
     * with the manager, either through long polling or webhook methods.
     * </p>
     *
     * @return an unmodifiable collection of all registered {@link TelegramBot} instances
     */
    @NotNull
    @Unmodifiable
    Collection<TelegramBot> getRegisteredBots();

}
