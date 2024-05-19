package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;

/**
 * Class representing a command syntax.
 * Every command can have multiple syntaxes, and this class is a representation of each syntax
 *
 * @param condition interface that checks if a command can be executed via certain conditions
 * @param executor the command executor
 * @param args array of command arguments
 */
public record CommandSyntax(
        @Nullable
        CommandCondition condition,

        @NotNull
        CommandExecutor executor,

        @NotNull
        Argument<?>[] args) {
}
