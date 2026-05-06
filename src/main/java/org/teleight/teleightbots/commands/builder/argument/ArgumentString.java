package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

/// Class representing a string argument.
public class ArgumentString extends Argument<String> {

    /// A boolean indicating whether this argument greedily matches the remaining input or not.
    private final boolean greedyMatch;

    /// Constructs an ArgumentString with the specified identifier.
    ///
    /// @param id          the identifier of the argument
    /// @param greedyMatch if `true`, this argument will consume all remaining input until the end of the message;
    ///                    if `false`, it will only consume a single word
    public ArgumentString(String id, boolean greedyMatch) {
        super(id);
        this.greedyMatch = greedyMatch;
    }

    /// Constructs an ArgumentString with the specified identifier.
    /// The `greedyMatch` field is set to false by default.
    ///
    /// @param id the identifier of the argument
    public ArgumentString(String id) {
        this(id, false);
    }

    /// Returns the input string as it is.
    ///
    /// @param input the input string to be parsed
    /// @return the input string as it is
    /// @throws ArgumentSyntaxException if the input string cannot be parsed
    @Override
    public String parse(String input) throws ArgumentSyntaxException {
        return input;
    }

    /// Returns whether this argument greedily consumes all remaining input.
    ///
    /// @return {@code true} if this argument consumes all remaining input, {@code false} otherwise
    public boolean greedy() {
        return greedyMatch;
    }

}
