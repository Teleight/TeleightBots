package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

/**
 * A functional interface that provides a Bot instance with a given token, username, UpdateProcessor, and BotSettings.
 */
public interface BotProvider {

    /**
     * Provides a Bot instance with a given token, username, UpdateProcessor, and BotSettings.
     *
     * @param token           The token of the Bot to provide.
     * @param username        The username of the Bot to provide.
     * @param updateProcessor The UpdateProcessor of the Bot to provide.
     * @param botSettings     The BotSettings of the Bot to provide. This can be null.
     * @return The provided Bot.
     */
    TelegramBot provide(@NotNull String token, @NotNull String username, @NotNull UpdateProcessor updateProcessor, @NotNull BotSettings botSettings);

}
