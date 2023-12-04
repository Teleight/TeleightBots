package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;

public interface CommandParser {

    static CommandParser parser(@NotNull CommandManager commandManager){
        return new CommandParserImpl(commandManager);
    }

    Result parse(Bot bot, @NotNull User sender, @NotNull String userInput, Message message);

    interface Result {
        @NotNull ExecutableCommand executable();
    }

}
