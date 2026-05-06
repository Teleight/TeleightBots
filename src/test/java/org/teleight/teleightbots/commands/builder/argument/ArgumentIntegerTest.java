package org.teleight.teleightbots.commands.builder.argument;

import org.junit.jupiter.api.Test;
import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgumentIntegerTest {

    @Test
    void parse_returnsParsedInteger() {
        ArgumentInteger argument = new ArgumentInteger("amount");

        assertEquals(42, argument.parse("42"));
        assertEquals(-7, argument.parse("-7"));
    }

    @Test
    void parse_throwsArgumentSyntaxExceptionOnInvalidInput() {
        ArgumentInteger argument = new ArgumentInteger("amount");

        ArgumentSyntaxException exception = assertThrows(ArgumentSyntaxException.class, () -> argument.parse("abc"));

        assertEquals("abc", exception.getInput());
        assertTrue(exception.getMessage() != null && !exception.getMessage().isEmpty());
    }
}

