package org.teleight.teleightbots.event.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.Bot;

import java.util.concurrent.CompletableFuture;

public interface Event {

    @NotNull Bot bot();

    default <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        return bot().execute(method);
    }

}
