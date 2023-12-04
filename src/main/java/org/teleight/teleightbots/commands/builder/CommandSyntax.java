package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;

public class CommandSyntax {

    private final CommandCondition condition;
    private final CommandExecutor executor;
    private final Argument<?>[] args;

    public CommandSyntax(@Nullable CommandCondition condition, CommandExecutor executor, Argument<?>[] args) {
        this.condition = condition;
        this.executor = executor;
        this.args = args;
    }

    public CommandCondition getCondition() {
        return condition;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public Argument<?>[] getArgs() {
        return args;
    }

}
