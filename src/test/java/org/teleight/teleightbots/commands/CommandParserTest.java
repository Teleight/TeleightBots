package org.teleight.teleightbots.commands;

import org.junit.jupiter.api.Test;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.CommandExecutor;
import org.teleight.teleightbots.commands.builder.argument.ArgumentInteger;
import org.teleight.teleightbots.commands.builder.argument.ArgumentString;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;
import org.teleight.teleightbots.utils.TestBotUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandParserTest {

    @Test
    void parse_executesMatchingSyntaxAndInjectsArguments() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicInteger captured = new AtomicInteger(-1);
        TestCommand command = new TestCommand("sum");
        ArgumentInteger amount = new ArgumentInteger("amount");
        command.addSyntax((_, ctx) -> captured.set(ctx.getArgument(amount)), amount);
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/sum 42", null);
        result.executable().execute(sender);

        assertEquals(42, captured.get());
    }

    @Test
    void parse_usesDefaultExecutorWhenSyntaxDoesNotMatch() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicBoolean defaultExecuted = new AtomicBoolean(false);
        AtomicBoolean syntaxExecuted = new AtomicBoolean(false);
        TestCommand command = new TestCommand("sum");
        ArgumentInteger amount = new ArgumentInteger("amount");
        command.addSyntax((_, _) -> syntaxExecuted.set(true), amount);
        command.defaultExecutor((_, _) -> defaultExecuted.set(true));
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/sum abc", null);
        result.executable().execute(sender);

        assertTrue(defaultExecuted.get());
        assertFalse(syntaxExecuted.get());
    }

    @Test
    void parse_honorsReadRemainingForStringArgument() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicReference<String> captured = new AtomicReference<>();
        TestCommand command = new TestCommand("say");
        ArgumentString text = new ArgumentString("text", true);
        command.addSyntax((_, ctx) -> captured.set(ctx.getArgument(text)), text);
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/say hello world from bot", null);
        result.executable().execute(sender);

        assertEquals("hello world from bot", captured.get());
    }

    @Test
    void parse_returnsInvalidWhenCommandConditionFails() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicBoolean executed = new AtomicBoolean(false);
        TestCommand command = new TestCommand("locked");
        command.condition((_, _, _) -> false);
        command.defaultExecutor((_, _) -> executed.set(true));
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/locked", null);
        result.executable().execute(sender);

        assertFalse(executed.get());
    }

    @Test
    void parse_selectsCompatibleSyntaxAmongMultipleCandidates() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicReference<String> chosen = new AtomicReference<>("none");
        TestCommand command = new TestCommand("value");
        command.addSyntax((_, _) -> chosen.set("int"), new ArgumentInteger("num"));
        command.addSyntax((_, _) -> chosen.set("text"), new ArgumentString("text"));
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/value hello", null);
        result.executable().execute(sender);

        assertEquals("text", chosen.get());
    }

    @Test
    void parse_skipsSyntaxWhenSyntaxConditionFails() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicReference<String> chosen = new AtomicReference<>("none");
        TestCommand command = new TestCommand("route");
        command.addConditionalSyntax((_, _, _) -> false, (_, _) -> chosen.set("blocked"), new ArgumentString("text"));
        command.addSyntax((_, _) -> chosen.set("fallback"), new ArgumentString("text"));
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/route hi", null);
        result.executable().execute(sender);

        assertEquals("fallback", chosen.get());
    }

    @Test
    void parse_returnsInvalidWhenNoMatchingSyntaxAndNoDefaultExecutor() {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        CommandManager commandManager = CommandManager.newCommandManager(bot);
        CommandParser parser = CommandParser.newCommandParser(commandManager);
        User sender = TestBotUtils.newUser(123L);

        AtomicBoolean executed = new AtomicBoolean(false);
        TestCommand command = new TestCommand("sum");
        command.addSyntax((_, _) -> executed.set(true), new ArgumentInteger("amount"));
        commandManager.registerCommand(command);

        CommandParser.Result result = parser.parse(bot, sender, "/sum abc", null);
        result.executable().execute(sender);

        assertFalse(executed.get());
    }



    private static final class TestCommand extends Command {
        private TestCommand(String name, String... aliases) {
            super(name, aliases);
        }

        private void defaultExecutor(CommandExecutor executor) {
            setDefaultExecutor(executor);
        }

        private void condition(CommandCondition condition) {
            setCondition(condition);
        }
    }
}
