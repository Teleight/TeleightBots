package org.teleight.teleightbots.event.user;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

public record UserInlineQueryReceivedEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements Event {

}
