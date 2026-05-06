package org.teleight.teleightbots.commands.builder.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgumentReaderTest {

    @Test
    void readWord_advancesCursorAndReturnsWords() {
        ArgumentReader reader = new ArgumentReader("one two three");

        assertEquals("one", reader.readWord());
        assertEquals("two", reader.readWord());
        assertEquals("three", reader.readWord());
        assertFalse(reader.hasRemaining());
    }

    @Test
    void readRemaining_returnsTailAndMovesCursorToEnd() {
        ArgumentReader reader = new ArgumentReader("one two three");
        reader.readWord();

        assertEquals("two three", reader.readRemaining());
        assertFalse(reader.hasRemaining());
    }

    @Test
    void resetCursor_movesBackToStart() {
        ArgumentReader reader = new ArgumentReader("one two");
        reader.readWord();
        reader.readRemaining();
        assertFalse(reader.hasRemaining());

        reader.resetCursor();

        assertTrue(reader.hasRemaining());
        assertEquals("one", reader.readWord());
    }
}

