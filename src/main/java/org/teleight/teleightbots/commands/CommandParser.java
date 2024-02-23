package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;

/**
 * Interface for parsing commands.
 */
public interface CommandParser {

    /**
     * Static factory method for creating a new CommandParser.
     *
     * @param commandManager the CommandManager to be used by the CommandParser
     * @return a new CommandParserImpl instance
     */
    static CommandParser parser(@NotNull CommandManager commandManager) {
        return new CommandParserImpl(commandManager);
    }

    /**
     * Parses user input into a command.
     *
     * @param bot       the Bot instance that received the command
     * @param sender    the User who sent the command
     * @param userInput the user's input as a String
     * @param message   the Message object containing the user's input
     * @return a Result object containing the parsed command
     */
    Result parse(Bot bot, @NotNull User sender, @NotNull String userInput, Message message);

    interface Result {
        /**
         * Gets the executable command from the parse result.
         *
         * @return the ExecutableCommand from the parse result
         */
        @NotNull ExecutableCommand executable();
    }

}
