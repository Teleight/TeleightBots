package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

/**
 * Interface for an executable command.
 */
public interface ExecutableCommand {

    /**
     * Executes the command.
     *
     * @param from the User who initiated the command
     */
    void execute(@NotNull User from);

}
