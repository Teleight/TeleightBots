package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

/**
 * Class representing a string argument.
 * <p>
 * This class extends the Argument class with a type parameter of String.
 * It overrides the parse method of the Argument class to return the input string as it is.
 */
public class ArgumentString extends Argument<String> {

    /**
     * A boolean indicating whether to read the remaining input or not.
     */
    private final boolean readRemaining;

    /**
     * Constructs an ArgumentString with the specified identifier and a boolean indicating whether to read the remaining input or not.
     *
     * @param id            the identifier of the argument
     * @param readRemaining a boolean indicating whether to read the remaining input or not
     */
    public ArgumentString(String id, boolean readRemaining) {
        super(id);
        this.readRemaining = readRemaining;
    }

    /**
     * Constructs an ArgumentString with the specified identifier.
     * The readRemaining field is set to false by default.
     *
     * @param id the identifier of the argument
     */
    public ArgumentString(String id) {
        this(id, false);
    }

    /**
     * Returns the input string as it is.
     *
     * @param input the input string to be parsed
     * @return the input string as it is
     * @throws ArgumentSyntaxException if the input string cannot be parsed
     */
    @Override
    public String parse(String input) throws ArgumentSyntaxException {
        return input;
    }

    /**
     * Returns the value of the readRemaining field.
     *
     * @return the value of the readRemaining field
     */
    public boolean readRemaining() {
        return readRemaining;
    }

}
