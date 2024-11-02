package org.teleight.teleightbots.commands;

import org.checkerframework.com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.commands.builder.Command;

import java.util.HashMap;
import java.util.Map;

final class CommandManagerImpl implements CommandManager {

    private final TelegramBot bot;
    private final Map<String, Command> commandMap = new HashMap<>();

    CommandManagerImpl(@NotNull TelegramBot bot){
        this.bot = bot;
    }

    @Override
    public synchronized void registerCommand(@NotNull Command command) {
        final String commandName = command.getName();
        final String[] commandAliases = command.getAliases();

        Preconditions.checkState(!commandExists(commandName), "A command with the name " + commandName + " is already registered!");
        for (String alias : commandAliases) {
            Preconditions.checkState(!commandExists(alias), "A command with the name " + alias + " is already registered!");
        }
        commandMap.put(commandName, command);
        for (String name : commandAliases) {
            commandMap.put(name, command);
        }
    }

    @Override
    public @Nullable Command getCommand(@NotNull String commandName) {
        return commandMap.get(commandName.toLowerCase());
    }

    @Override
    public boolean commandExists(@NotNull String commandName) {
        return getCommand(commandName) != null;
    }

    @Override
    public void execute(@NotNull User sender, @NotNull String userInput, Message message) {
        final CommandParser commandParser = CommandParser.newCommandParser(this);
        try {
            final CommandParser.Result result = commandParser.parse(bot, sender, userInput, message);
            final ExecutableCommand executableCommand = result.executable();
            executableCommand.execute(sender);
        } catch (Throwable t) {
            TeleightBots.getExceptionManager().handleException(t);
        }
    }

    @Override
    public @Nullable String extractCommand(@NotNull String userInput) {
        final String[] split = userInput.split(" ");
        if (split.length == 0) {
            return userInput;
        }
        final String firstArgument = split[0];
        String commandAsString = null;
        if (userInput.startsWith("@") || !firstArgument.contains("@")) {
            commandAsString = firstArgument.substring(1);
        } else {
            final int index = userInput.indexOf("@");
            final String botName = firstArgument.substring(index + 1);
            if (botName.equalsIgnoreCase(bot.getBotUsername())) {
                commandAsString = firstArgument.substring(1, index);
            }
        }
        return commandAsString;
    }

}
