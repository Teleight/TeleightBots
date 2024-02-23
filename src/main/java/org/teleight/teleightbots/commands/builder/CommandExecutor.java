package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.commands.builder.parser.CommandContext;

/**
 * Functional interface for a command executor.
 */
@FunctionalInterface
public interface CommandExecutor {

    /**
     * Executes the command.
     *
     * @param sender  the User who initiated the command
     * @param context the CommandContext associated with the command
     */
    void execute(@NotNull User sender, @NotNull CommandContext context);

}
