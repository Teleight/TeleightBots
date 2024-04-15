package org.teleight.teleightbots;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.botmanager.BotManager;
import org.teleight.teleightbots.botmanager.BotManagerImpl;
import org.teleight.teleightbots.exception.ExceptionManager;

final class TeleightBotsProcessImpl implements TeleightBotsProcess {

    private final BotManagerImpl botManagerImpl;
    private final ExceptionManager exceptionManager;

    public TeleightBotsProcessImpl() {
        botManagerImpl = new BotManagerImpl();
        exceptionManager = new ExceptionManager();
    }

    @Override
    public void start() {
        TeleightBots.getGlobalLogger().info("Starting bot process");
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        TeleightBots.getGlobalLogger().info("Shutting down bot process");
        botManagerImpl.close();
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
