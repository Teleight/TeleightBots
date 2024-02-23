package org.teleight.teleightbots.commands.builder.parser;

import org.jetbrains.annotations.NotNull;

/**
 * Class representing an argument reader.
 */
public final class ArgumentReader {

    /**
     * The input string from which arguments are read.
     */
    private final String input;

    /**
     * The current position in the input string.
     */
    private int cursor = 0;

    /**
     * Constructs an ArgumentReader with the specified input string.
     *
     * @param input the input string from which arguments are read
     */
    public ArgumentReader(String input) {
        this.input = input;
    }

    /**
     * Checks if there are more characters to read in the input string.
     *
     * @return true if there are more characters to read, false otherwise
     */
    public boolean hasRemaining() {
        return cursor < input.length();
    }

    /**
     * Reads the next word from the input string.
     * <p>
     * A word is defined as a sequence of characters separated by spaces.
     * The cursor is moved to the position after the read word.
     *
     * @return the read word
     */
    public @NotNull String readWord() {
        final String input = this.input;
        final int cursor = this.cursor;

        final int index = input.indexOf(" ", cursor);
        if (index == -1) {
            this.cursor = input.length() + 1;
            return input.substring(cursor);
        }
        final String read = input.substring(cursor, index);
        this.cursor += read.length() + 1;
        return read;
    }

    /**
     * Reads the remaining characters from the input string.
     * <p>
     * The cursor is moved to the end of the input string.
     *
     * @return the remaining characters
     */
    public @NotNull String readRemaining() {
        final String input = this.input;
        final String result = input.substring(cursor);
        this.cursor = input.length();
        return result;
    }

    /**
     * Resets the cursor to the start of the input string.
     */
    public void resetCursor() {
        this.cursor = 0;
    }

}
