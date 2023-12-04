package org.teleight.teleightbots.commands.builder.parser;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.commands.builder.argument.Argument;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CommandContext {

    private final Bot bot;
    private final Message message;
    private final String input;

    private final Map<String, Object> arguments = new HashMap<>();

    public CommandContext(Bot bot, Message message, String input) {
        this.bot = bot;
        this.message = message;
        this.input = input;
    }

    public @NotNull String getInput() {
        return input;
    }

    public <T> T getArgument(Argument<T> argument) {
        return (T) arguments.get(argument.getId());
    }

    public void setArgument(String argumentId, Object value) {
        arguments.put(argumentId, value);
    }

    public Message message() {
        return message;
    }

    public Bot bot() {
        return bot;
    }

    public <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        return bot.execute(method);
    }

}
