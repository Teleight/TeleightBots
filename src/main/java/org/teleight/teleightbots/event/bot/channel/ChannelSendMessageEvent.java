package org.teleight.teleightbots.event.bot.channel;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

public record ChannelSendMessageEvent(
        @NotNull Bot bot,
        @NotNull Update update,
        @NotNull Chat chat
) implements Event {

    public @NotNull Message channelPost(){
        return update.channelPost();
    }

}
