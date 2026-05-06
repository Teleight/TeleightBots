package org.teleight.teleightbots.commands;

import org.junit.jupiter.api.Test;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.CommandExecutor;
import org.teleight.teleightbots.utils.TestBotUtils;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandManagerTest {

    @Test
    void registerCommand_registersNameAndAliases() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));
        TestCommand command = new TestCommand("ping", "p");

        commandManager.registerCommand(command);

        assertTrue(commandManager.commandExists("ping"));
        assertTrue(commandManager.commandExists("p"));
        assertSame(command, commandManager.getCommand("PING"));
        assertSame(command, commandManager.getCommand("P"));
    }

    @Test
    void registerCommand_throwsWhenNameAlreadyExists() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));
        commandManager.registerCommand(new TestCommand("ping"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> commandManager.registerCommand(new TestCommand("ping")));

        assertTrue(exception.getMessage().contains("already registered"));
    }

    @Test
    void registerCommand_throwsWhenNameConflictsWithExistingAlias() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));
        commandManager.registerCommand(new TestCommand("ping", "p"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> commandManager.registerCommand(new TestCommand("p")));

        assertTrue(exception.getMessage().contains("already registered"));
    }

    @Test
    void registerCommand_throwsWhenAliasConflictsWithExistingName() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));
        commandManager.registerCommand(new TestCommand("ping"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> commandManager.registerCommand(new TestCommand("pong", "ping")));

        assertTrue(exception.getMessage().contains("already registered"));
    }

    @Test
    void extractCommand_handlesDirectAndMentionedCommands() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));

        assertEquals("ping", commandManager.extractCommand("/ping"));
        assertEquals("ping", commandManager.extractCommand("/ping@MyBot"));
        assertEquals("ping", commandManager.extractCommand("/ping@mybot"));
        assertNull(commandManager.extractCommand("/ping@OtherBot"));
    }

    @Test
    void extractCommand_returnsNullForMalformedOrNonCommandInput() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));

        assertNull(commandManager.extractCommand(""));
        assertNull(commandManager.extractCommand("   "));
        assertNull(commandManager.extractCommand("hello"));
        assertNull(commandManager.extractCommand("/"));
        assertNull(commandManager.extractCommand("/ping@"));
    }

    @Test
    void execute_catchesExecutorExceptionAndRoutesToExceptionManager() {
        CommandManager commandManager = CommandManager.newCommandManager(TestBotUtils.newBot("MyBot"));
        RuntimeException boom = new RuntimeException("boom!!");
        TestCommand command = new TestCommand("explode");
        command.defaultExecutor((_, _) -> {
            throw boom;
        });
        commandManager.registerCommand(command);

        AtomicReference<Throwable> captured = new AtomicReference<>();
        TeleightBots.getExceptionManager().setExceptionHandler(captured::set);

        assertDoesNotThrow(() -> commandManager.execute(TestBotUtils.newUser(100L), "/explode", null));
        assertSame(boom, captured.get());
    }

    private static final class TestCommand extends Command {
        private TestCommand(String name, String... aliases) {
            super(name, aliases);
            setDefaultExecutor((_, _) -> {});
        }

        private void defaultExecutor(CommandExecutor executor) {
            setDefaultExecutor(executor);
        }
    }
}
