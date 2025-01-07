package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;

/**
 * Represents a Telegram bot that uses long polling to receive updates.
 */
public sealed interface LongPollingTelegramBot extends TelegramBot permits LongPollingTelegramBotImpl {

    @ApiStatus.Internal
    static @NotNull LongPollingTelegramBot create(@NotNull String token,
                                                  @NotNull String username,
                                                  @NotNull LongPollingBotSettings longPollingSettings) {
        return new LongPollingTelegramBotImpl(token, username, longPollingSettings);
    }

    /**
     * Gets the long polling bot settings.
     *
     * @return The long polling bot settings. Can't be null.
     */
    @NotNull LongPollingBotSettings getBotSettings();

}
