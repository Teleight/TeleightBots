package org.teleight.teleightbots.commands.builder.argument;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgumentStringTest {

    @Test
    void parse_returnsSameInput() {
        ArgumentString argument = new ArgumentString("text");
        assertEquals("hello world", argument.parse("hello world"));
    }

    @Test
    void greedy_defaultsToFalse() {
        ArgumentString argument = new ArgumentString("text");
        assertFalse(argument.greedy());
    }

    @Test
    void greedy_canBeEnabled() {
        ArgumentString argument = new ArgumentString("text", true);
        assertTrue(argument.greedy());
    }
}

