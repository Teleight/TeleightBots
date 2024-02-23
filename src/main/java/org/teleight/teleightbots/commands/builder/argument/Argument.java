package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

/**
 * Abstract class representing an argument.
 *
 * @param <T> the type of the argument
 */
public abstract class Argument<T> {

    /**
     * The identifier of the argument.
     */
    protected final String id;

    /**
     * Constructs an Argument with the specified identifier.
     *
     * @param id the identifier of the argument
     */
    public Argument(String id) {
        this.id = id;
    }

    /**
     * Returns the identifier of the argument.
     *
     * @return the identifier of the argument
     */
    public String getId() {
        return id;
    }

    /**
     * Parses the input into an object of type T.
     *
     * @param input the input to be parsed
     * @return the parsed object
     * @throws ArgumentSyntaxException if the input cannot be parsed
     */
    public abstract T parse(String input) throws ArgumentSyntaxException;

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Argument<?> argument = (Argument<?>) o;

        return id.equals(argument.id);
    }

    /**
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
