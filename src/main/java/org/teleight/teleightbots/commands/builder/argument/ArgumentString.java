package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

public class ArgumentString extends Argument<String> {

    private final boolean readRemaining;

    public ArgumentString(String id, boolean readRemaining) {
        super(id);
        this.readRemaining = readRemaining;
    }

    public ArgumentString(String id) {
        this(id, false);
    }

    @Override
    public String parse(String input) throws ArgumentSyntaxException {
        return input;
    }

    public boolean readRemaining() {
        return readRemaining;
    }

}
