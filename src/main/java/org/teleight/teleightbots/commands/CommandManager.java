package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.commands.builder.Command;

public interface CommandManager {

    void registerCommand(@NotNull Command command);

    @Nullable Command getCommand(@NotNull String commandName);

    boolean commandExists(@NotNull String commandName);

    void execute(@NotNull User sender, @NotNull String fullText, Message message);

    String extractCommand(String text);

}
