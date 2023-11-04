package org.teleight.teleightbots.botmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;

import java.util.function.Consumer;

public sealed interface BotManager permits BotManagerImpl {

    void registerLongPolling(@NotNull String token, @NotNull String username, @Nullable BotSettings botSettings, @Nullable Consumer<Bot> completeCallback);

    void registerLongPolling(@NotNull String token, @NotNull String username, @Nullable BotSettings botSettings);

    BotProvider getBotProvider();

    void setBotProvider(@NotNull BotProvider botProvider);

}
