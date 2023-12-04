package org.teleight.teleightbots.commands.builder.exception;

import org.jetbrains.annotations.NotNull;

public class ArgumentSyntaxException extends RuntimeException {

    private final String input;

    public ArgumentSyntaxException(@NotNull String message, @NotNull String input) {
        super(message);
        this.input = input;
    }

    @NotNull
    public String getInput() {
        return input;
    }

}
