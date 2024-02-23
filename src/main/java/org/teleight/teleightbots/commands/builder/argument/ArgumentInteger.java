package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

/**
 * Class representing an integer argument.
 * <p>
 * This class extends the Argument class with a type parameter of Integer.
 * It overrides the parse method of the Argument class to parse a string into an integer.
 */
public class ArgumentInteger extends Argument<Integer> {

    /**
     * Constructs an ArgumentInteger with the specified identifier.
     *
     * @param id the identifier of the argument
     */
    public ArgumentInteger(String id) {
        super(id);
    }

    /**
     * Parses the input string into an integer.
     *
     * @param input the input string to be parsed
     * @return the parsed integer
     * @throws ArgumentSyntaxException if the input string cannot be parsed into an integer
     */
    @Override
    public Integer parse(String input) throws ArgumentSyntaxException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ArgumentSyntaxException(e.getMessage(), input);
        }
    }

}
