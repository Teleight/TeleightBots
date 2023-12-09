package org.teleight.teleightbots.event.bot.group;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.GroupBotEvent;

public record BotJoinGroupEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements GroupBotEvent {

}
