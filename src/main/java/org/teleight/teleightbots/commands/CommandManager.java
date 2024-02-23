package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.commands.builder.Command;

/**
 * Basic interface for a CommandManager. Provides methods to manage commands.
 * @see Bot#getCommandManager()
 */
public interface CommandManager {

    /**
     * Registers a new command from a Command instance.
     * @param command The command to be registered.
     */
    void registerCommand(@NotNull Command command);

    /**
     * Returns a Command by its name.
     * @param commandName The name of the Command to return.
     * @return The Command with the given name, or null if it does not exist.
     */
    @Nullable Command getCommand(@NotNull String commandName);

    /**
     * Checks if a Command exists.
     * @param commandName The name of the Command to check.
     * @return True if the Command exists, false otherwise.
     */
    boolean commandExists(@NotNull String commandName);

    /**
     * Executes a Command.
     * @param sender The User who is executing the Command.
     * @param fullText The full text of the Command.
     * @param message The Message that contains the Command.
     */
    void execute(@NotNull User sender, @NotNull String fullText, Message message);

    /**
     * Extracts a Command from a text string.
     * @param text The text string to extract the Command from.
     * @return The extracted Command.
     */
    String extractCommand(String text);

}
