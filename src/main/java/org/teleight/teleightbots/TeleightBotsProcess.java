package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.botmanager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;

public sealed interface TeleightBotsProcess permits TeleightBotsProcessImpl {

    void start();

    void close();

    @NotNull BotManager botManager();

    @NotNull ExceptionManager exceptionManager();

}
