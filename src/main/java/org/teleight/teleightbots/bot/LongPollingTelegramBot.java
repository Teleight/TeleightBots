package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

public final class LongPollingTelegramBot extends BaseTelegramBot<LongPollingBotSettings> {

    public LongPollingTelegramBot(@NotNull String token,
                                  @NotNull String username,
                                  @NotNull UpdateProcessor<LongPollingTelegramBot> updateProcessor,
                                  @NotNull LongPollingBotSettings botSettings) {
        super(token, username, updateProcessor, botSettings);
    }

}
