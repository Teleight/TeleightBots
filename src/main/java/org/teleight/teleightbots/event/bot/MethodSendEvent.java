package org.teleight.teleightbots.event.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.OutgoingEvent;

public record MethodSendEvent<R>(
        @NotNull Bot bot,
        @NotNull ApiMethod<R> method,
        @NotNull R result
) implements OutgoingEvent {

}
