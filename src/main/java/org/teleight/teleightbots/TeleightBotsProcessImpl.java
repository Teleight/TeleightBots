package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.botmanager.BotManager;
import org.teleight.teleightbots.botmanager.BotManagerImpl;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.scheduler.SchedulerImpl;

final class TeleightBotsProcessImpl implements TeleightBotsProcess {

    private final BotManagerImpl botManagerImpl;
    private final Scheduler schedulerImpl;
    private final ExceptionManager exceptionManager;

    public TeleightBotsProcessImpl() {
        schedulerImpl = new SchedulerImpl();
        botManagerImpl = new BotManagerImpl();
        exceptionManager = new ExceptionManager();
    }

    @Override
    public void start() {
        TeleightBots.getLogger().info("Starting bot process");
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        TeleightBots.getLogger().info("Shutting down bot process");
        botManagerImpl.close();
    }

    @Override
    public @NotNull Scheduler scheduler() {
        return schedulerImpl;
    }

    @Override
    public @NotNull BotManager botManager() {
        return botManagerImpl;
    }

    @Override
    public @NotNull ExceptionManager exceptionManager() {
        return exceptionManager;
    }

}
