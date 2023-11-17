package org.teleight.teleightbots.commands.builder.parser;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.commands.builder.argument.Argument;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {

    private final String input;

    private final Map<String, Object> arguments = new HashMap<>();

    public CommandContext(String input) {
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

}
