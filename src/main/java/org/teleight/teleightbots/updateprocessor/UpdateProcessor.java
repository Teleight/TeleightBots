package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.Bot;

import java.util.concurrent.CompletableFuture;

public interface UpdateProcessor {

    void start();

    void terminate();

    @NotNull CompletableFuture<String> executeMethod(@NotNull ApiMethod<?> method);

    void setBot(@NotNull Bot bot);

}
