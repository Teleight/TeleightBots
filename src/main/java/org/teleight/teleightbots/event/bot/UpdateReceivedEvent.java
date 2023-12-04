package org.teleight.teleightbots.event.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;
import org.teleight.teleightbots.event.trait.IngoingEvent;

public record UpdateReceivedEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements IngoingEvent {

}
