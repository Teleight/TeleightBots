package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

public class ArgumentString extends Argument<String> {

    public ArgumentString(String id) {
        super(id);
    }

    @Override
    public String parse(String input) throws ArgumentSyntaxException {
        return input;
    }

}
