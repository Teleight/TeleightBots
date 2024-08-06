package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.manager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;

import java.io.Closeable;

public sealed interface TeleightBotsProcess extends Closeable permits TeleightBotsProcessImpl {

    @NotNull Scheduler scheduler();

    @NotNull BotManager botManager();

    @NotNull
    ExceptionManager exceptionManager();

}
