package org.teleight.teleightbots.commands;

import com.github.javaparser.quality.NotNull;
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
