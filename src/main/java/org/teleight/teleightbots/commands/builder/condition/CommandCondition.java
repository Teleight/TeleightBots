package org.teleight.teleightbots.commands.builder.condition;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;

@FunctionalInterface
public interface CommandCondition {

    boolean canUse(@NotNull Bot bot, @NotNull User sender, @NotNull Message message);

}
