package org.teleight.teleightbots.commands.builder.condition;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;

/**
 * Functional interface representing a command condition.
 * <p>
 * This interface provides a single method to check if a command can be used.
 */
@FunctionalInterface
public interface CommandCondition {

    /**
     * Checks if a command can be used by a specific user in a specific message context.
     *
     * @param bot     the bot that received the command
     * @param sender  the user who sent the command
     * @param message the message containing the command
     * @return true if the command can be used, false otherwise
     */
    boolean canUse(@NotNull Bot bot, @NotNull User sender, @NotNull Message message);

}
