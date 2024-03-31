package org.teleight.teleightbots.commands.builder.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Class representing an argument syntax exception.
 * <p>
 * Thrown when an argument cannot be parsed correctly.
 */
public class ArgumentSyntaxException extends RuntimeException {

    /**
     * The input string that caused the exception.
     */
    private final String input;

    /**
     * Constructs an ArgumentSyntaxException with the specified message and input string.
     *
     * @param message the detail message, saved for later retrieval by the getMessage() method
     * @param input   the input string that caused the exception
     */
    public ArgumentSyntaxException(@NotNull String message, @NotNull String input) {
        super(message);
        this.input = input;
    }

    /**
     * Returns the input string that caused the exception.
     *
     * @return the input string that caused the exception
     */
    @NotNull
    public String getInput() {
        return input;
    }

}
