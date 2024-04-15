package org.teleight.teleightbots.exception;

import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.Bot;

public class ExceptionManager {

    private ExceptionHandler exceptionHandler;

    public void handleException(Bot bot, Throwable e) {
        if (e instanceof OutOfMemoryError) {
            // OOM should be handled manually
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            TeleightBots.stopCleanly();
            return;
        }
        getExceptionHandler(bot).handleException(e);
    }

    public ExceptionHandler getExceptionHandler(Bot bot) {
        if (exceptionHandler == null) {
            exceptionHandler = t -> {
                bot.getLogger().error("An exception has occurred, but no exception handler was set.\n" +
                        "You can set an exception handler by using TeleightBots.getExceptionHandler().setExceptionHandler()", t);
            };
        }
        return exceptionHandler;
    }

    public void setExceptionHandler(@Nullable ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

}
