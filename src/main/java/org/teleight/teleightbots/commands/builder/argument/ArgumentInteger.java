package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

public class ArgumentInteger extends Argument<Integer> {

    public ArgumentInteger(String id) {
        super(id);
    }

    @Override
    public Integer parse(String input) throws ArgumentSyntaxException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ArgumentSyntaxException(e.getMessage(), input);
        }
    }

}
