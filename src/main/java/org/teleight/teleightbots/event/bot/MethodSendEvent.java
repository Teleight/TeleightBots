package org.teleight.teleightbots.event.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

import java.io.Serializable;

public record MethodSendEvent<R extends Serializable>(
        @NotNull TelegramBot bot,
        @NotNull ApiMethod<R> method,
        @NotNull R result
) implements Event {

}
