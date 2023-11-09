package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

public class CommandContext {

    private final User sender;
    private final String input;
    private final Command command;

    public CommandContext(User sender, String input, Command command) {
        this.sender = sender;
        this.input = input;
        this.command = command;
    }

    public @NotNull User getSender() {
        return sender;
    }

    public @NotNull String getInput() {
        return input;
    }

    public @NotNull Command getCommand() {
        return command;
    }

}
