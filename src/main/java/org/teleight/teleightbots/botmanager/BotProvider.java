package org.teleight.teleightbots.botmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

/**
 * A functional interface that provides a Bot instance with a given token, username, UpdateProcessor, and BotSettings.
 */
@FunctionalInterface
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
    Bot provide(@NotNull String token, @NotNull String username, @NotNull UpdateProcessor updateProcessor, @Nullable BotSettings botSettings);

}
