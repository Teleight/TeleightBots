package org.teleight.teleightbots.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Interface representing an exception handler.
 */
@FunctionalInterface
public interface ExceptionHandler {

    /**
     * Handles the provided exception.
     *
     * @param t the exception to be handled
     */
    void handleException(@NotNull Throwable t);

}
