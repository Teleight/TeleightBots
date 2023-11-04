package org.teleight.teleightbots.exception;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExceptionHandler {

    void handleException(@NotNull Throwable t);

}
