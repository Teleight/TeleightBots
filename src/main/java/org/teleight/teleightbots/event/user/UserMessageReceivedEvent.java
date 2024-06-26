package org.teleight.teleightbots.event.user;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.MessageEvent;

public record UserMessageReceivedEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update
) implements MessageEvent {

    @Override
    public @NotNull Update update() {
        return update;
    }

    public Message message(){
        return update.message();
    }

}
