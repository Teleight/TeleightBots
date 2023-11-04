package org.teleight.teleightbots;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.botmanager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;

public final class TeleightBots {

    private static TeleightBotsProcess teleightBotsProcess;

    public static @NotNull TeleightBots init() {
        updateProcess();
        return new TeleightBots();
    }

    @ApiStatus.Internal
    public static TeleightBotsProcess updateProcess() {
        TeleightBotsProcess process = new TeleightBotsProcessImpl();
        teleightBotsProcess = process;
        return process;
    }

    public static void stopCleanly() {
        teleightBotsProcess.close();
    }

    public static @NotNull Scheduler getScheduler() {
        return teleightBotsProcess.scheduler();
    }

    public static @NotNull BotManager getBotManager() {
        return teleightBotsProcess.botManager();
    }

    public static @NotNull ExceptionManager getExceptionManager() {
        return teleightBotsProcess.exceptionManager();
    }

    public void start() {
        teleightBotsProcess.start();
    }

}
