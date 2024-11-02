package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.manager.BotManager;
import org.teleight.teleightbots.exception.ExceptionManager;
import org.teleight.teleightbots.scheduler.Scheduler;

import java.io.IOException;

final class TeleightBotsProcessImpl implements TeleightBotsProcess {

    private final Scheduler scheduler;
    private final BotManager botManager;
    private final ExceptionManager exceptionManager;

    public TeleightBotsProcessImpl() {
        scheduler = Scheduler.newScheduler();
        botManager = BotManager.newBotManager();
        exceptionManager = new ExceptionManager();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.close();
            } catch (IOException e) {
                TeleightBots.getExceptionManager().handleException(e);
            }
        }));
    }

    @Override
    public void close() throws IOException {
        System.out.println("Shutting down Teleight");
        botManager.close();
        scheduler.close();
    }

    @Override
    public @NotNull Scheduler scheduler() {
        return scheduler;
    }

    @Override
    public @NotNull BotManager botManager() {
        return botManager;
    }

    @Override
    public @NotNull ExceptionManager exceptionManager() {
        return exceptionManager;
    }

}
