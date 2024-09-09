package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public interface UpdateProcessor extends Closeable {

    CompletableFuture<User> start();

    @NotNull CompletableFuture<String> executeMethod(@NotNull ApiMethod<?> method);

    void setBot(@NotNull TelegramBot bot);

}
