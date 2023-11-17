package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.commands.builder.parser.CommandContext;

@FunctionalInterface
public interface CommandExecutor {

    void execute(@NotNull User sender, @NotNull CommandContext context);

}
