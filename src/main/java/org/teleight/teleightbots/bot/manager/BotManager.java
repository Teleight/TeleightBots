package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.WebhookTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.WebhookServer;
import org.teleight.teleightbots.webhook.WebhookServerConfig;

import java.io.Closeable;
import java.util.Collection;
import java.util.function.Consumer;

/// Interface for managing bots and their settings.
///
/// The `BotManager` provides methods to register and manage bots, supporting both
/// long polling and webhook-based Telegram bots. It also allows for retrieving the bots
/// that are currently registered with the manager.
///
@NotNullByDefault
public sealed interface BotManager extends Closeable permits BotManagerImpl {

    @ApiStatus.Internal
    static BotManager newBotManager() {
        return new BotManagerImpl();
    }

    /// Registers a new long polling bot with the default settings.
    ///
    /// This method simplifies the bot registration process by using default settings provided
    /// by [LongPollingBotSettings#DEFAULT]. After the bot is successfully registered,
    /// the provided `completeCallback` will be invoked.
    ///
    /// @param token            the bot's token, required for authentication with Telegram
    /// @param username         the bot's username, as it appears in Telegram
    /// @param completeCallback the callback that will be invoked when the bot has been registered
    /// @throws NullPointerException if any of the arguments are `null`
    /// @see #registerLongPolling(String, String, LongPollingBotSettings, Consumer)
    default void registerLongPolling(String token, 
                                     String username, 
                                     Consumer<LongPollingTelegramBot> completeCallback) {
        registerLongPolling(token, username, LongPollingBotSettings.DEFAULT, completeCallback);
    }

    /// Registers a new long polling bot with custom settings.
    ///
    /// This method allows for fine-grained control over the bot's behavior by accepting
    /// a [LongPollingBotSettings] object. The bot is registered with these settings
    /// and initialized accordingly. Upon successful registration, the `completeCallback`
    /// will be invoked.
    ///
    /// @param token               the bot's token, required for authentication with Telegram
    /// @param username            the bot's username, as it appears in Telegram
    /// @param longPollingSettings the settings to configure the long polling bot
    /// @param completeCallback    the callback that will be invoked when the bot has been registered
    /// @throws NullPointerException if any of the arguments are `null`
    void registerLongPolling(String token, 
                             String username, 
                             LongPollingBotSettings longPollingSettings, 
                             Consumer<LongPollingTelegramBot> completeCallback);

    /// Registers a new webhook-based bot with no internal server configuration.
    ///
    /// This method registers a bot that communicates with Telegram using webhooks.
    /// It uses no internal server for handling requests, so the bot will not receive
    /// updates until a server is set up externally.
    /// Once the bot is registered, the provided `completeCallback` will be invoked.
    ///
    /// @param token            the bot's token, required for authentication with Telegram
    /// @param username         the bot's username, as it appears in Telegram
    /// @param webhookSettings  the settings to configure the webhook bot
    /// @param webhookServer    the server to handle incoming webhook requests.
    /// @param completeCallback the callback that will be invoked when the bot has been registered
    /// @throws NullPointerException if any of the arguments are `null`
    /// @see #registerWebhook(String, String, WebhookBotSettings, WebhookServerConfig, Consumer)
    void registerWebhook(String token, 
                         String username, 
                         WebhookBotSettings webhookSettings, 
                         WebhookServer webhookServer, 
                         Consumer<WebhookTelegramBot> completeCallback);

    /// Registers a new webhook-based bot with an internal server configuration.
    ///
    /// This method allows configuring both the bot settings and the internal server configuration
    /// for a webhook-based bot. Once registered, the `completeCallback` will be invoked.
    ///
    /// @param token            the bot's token, required for authentication with Telegram
    /// @param username         the bot's username, as it appears in Telegram
    /// @param webhookSettings  the settings to configure the webhook bot
    /// @param serverConfig     the server configuration for handling webhook requests.
    /// @param completeCallback the callback that will be invoked when the bot has been registered
    /// @throws NullPointerException if any of the arguments are `null`
    void registerWebhook(String token, 
                         String username, 
                         WebhookBotSettings webhookSettings, 
                         WebhookServerConfig serverConfig, 
                         Consumer<WebhookTelegramBot> completeCallback);

    /// Unregisters a bot from the manager.
    ///
    /// This method removes the specified bot from the manager, effectively stopping
    /// the bot and cleaning up any resources associated with it.
    ///
    /// @param botToken the bot (token) to unregister
    /// @throws NullPointerException if the bot is `null`
    void unregisterBot(String botToken);

    /// Unregisters a bot from the manager.
    ///
    /// This method removes the specified bot from the manager, effectively stopping
    /// the bot and cleaning up any resources associated with it.
    ///
    /// @param telegramBot the bot to unregister
    /// @throws NullPointerException if the bot is `null`
    void unregisterBot(TelegramBot telegramBot);

    /// Retrieves all the bots currently managed by the `BotManager`.
    ///
    /// This method returns an unmodifiable collection of all the bots registered
    /// with the manager, either through long polling or webhook methods.
    ///
    /// @return an unmodifiable collection of all registered [TelegramBot] instances
    @Unmodifiable
    Collection<TelegramBot> getRegisteredBots();

}
