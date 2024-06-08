package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.manager.BotManager;
import org.teleight.teleightbots.bot.manager.BotManagerImpl;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.scheduler.SchedulerImpl;

final class TeleightBotsProcessImpl implements TeleightBotsProcess {

    private final SchedulerImpl schedulerImpl;
    private final BotManagerImpl botManagerImpl;
    private final ExceptionManager exceptionManager;

    public TeleightBotsProcessImpl() {
        schedulerImpl = new SchedulerImpl();
        botManagerImpl = new BotManagerImpl();
        exceptionManager = new ExceptionManager();
    }

    @Override
    public void start() {
        System.out.println("Started Teleight");
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        System.out.println("Shutting down Teleight");
        botManagerImpl.close();
        schedulerImpl.close();
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
