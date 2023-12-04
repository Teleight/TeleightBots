package org.teleight.teleightbots.commands.builder.parser;

import org.jetbrains.annotations.NotNull;

public final class ArgumentReader {

    private final String input;
    private int cursor = 0;

    public ArgumentReader(String input) {
        this.input = input;
    }

    public boolean hasRemaining() {
        return cursor < input.length();
    }

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

    public void resetCursor() {
        this.cursor = 0;
    }

}
