package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
    default void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull Consumer<TelegramBot> completeCallback) {
        registerLongPolling(token, username, BotSettings.DEFAULT, completeCallback);
    }

    /**
     * Registers a new long polling bot with the given token and username.
     * <p>
     * The bot will be initialized with custom settings provided by the given {@link BotSettings} object.
     * </p>
     *
     * @param token            the bot's token
     * @param username         the bot's username
     * @param botSettings      the bot's settings
     * @param completeCallback the callback to be called when the bot has been registered
     */
    void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull BotSettings botSettings, @NotNull Consumer<TelegramBot> completeCallback);

    /**
     * Returns an array of all the bots managed by the BotManager.
     *
     * @return An array of all the bots managed by the BotManager.
     */
    @NotNull @Unmodifiable Collection<TelegramBot> getRegisteredBots();

}
