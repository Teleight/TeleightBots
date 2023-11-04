package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.botmanager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;

public sealed interface TeleightBotsProcess permits TeleightBotsProcessImpl {

    void start();

    void close();

    @NotNull Scheduler scheduler();

    @NotNull BotManager botManager();

    @NotNull ExceptionManager exceptionManager();

}
