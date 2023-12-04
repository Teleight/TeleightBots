package org.teleight.teleightbots.commands.builder;

import org.teleight.teleightbots.commands.builder.argument.Argument;

public class CommandSyntax {

    private final CommandExecutor executor;
    private final Argument<?>[] args;

    public CommandSyntax(CommandExecutor executor, Argument<?>[] args) {
        this.executor = executor;
        this.args = args;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public Argument<?>[] getArgs() {
        return args;
    }

}
