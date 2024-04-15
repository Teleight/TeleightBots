package org.teleight.teleightbots.exception;

import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;

public class ExceptionManager {

    private ExceptionHandler exceptionHandler;

    public void handleException(Throwable e) {
        if (e instanceof OutOfMemoryError) {
            // OOM should be handled manually
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            TeleightBots.stopCleanly();
            return;
        }
        getExceptionHandler().handleException(e);
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler == null ? exceptionHandler = Throwable::printStackTrace : exceptionHandler;
    }

    public void setExceptionHandler(@Nullable ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

}
