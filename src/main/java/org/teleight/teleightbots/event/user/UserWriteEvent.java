package org.teleight.teleightbots.event.user;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

public record UserWriteEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements Event {

    @Override
    public @NotNull Update update() {
        return update;
    }

    public Message message(){
        return update.message();
    }

}
