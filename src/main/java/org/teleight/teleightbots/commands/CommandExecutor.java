package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface CommandExecutor {

    void execute(@NotNull CommandContext context);

}
