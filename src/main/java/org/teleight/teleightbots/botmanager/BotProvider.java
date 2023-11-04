package org.teleight.teleightbots.botmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

@FunctionalInterface
public interface BotProvider {

    Bot provide(@NotNull String token, @NotNull String username, @NotNull UpdateProcessor updateProcessor, @Nullable BotSettings botSettings);

}
