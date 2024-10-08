package org.teleight.teleightbots.event.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.TelegramBot;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Basic interface for an event.
 */
public interface Event {

    /**
     * Gets the Bot instance associated with the event.
     *
     * @return the Bot instance associated with the event
     */
    @NotNull
    TelegramBot bot();

    /**
     * Executes an API method.
     *
     * @param method the API method to be executed
     * @return a CompletableFuture that will complete with the result of the API method execution
     */
    default <R extends Serializable> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        return bot().execute(method);
    }

}
