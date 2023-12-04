package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;

public interface CommandParser {

    static CommandParser parser(@NotNull CommandManager commandManager){
        return new CommandParserImpl(commandManager);
    }

    Result parse(@NotNull String userInput);

    interface Result {
        @NotNull ExecutableCommand executable();
    }

}
