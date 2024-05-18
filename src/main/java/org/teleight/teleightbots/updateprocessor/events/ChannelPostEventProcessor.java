package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.bot.channel.ChannelSendMessageEvent;

public final class ChannelPostEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull Bot bot, @NotNull Update update) {
        final Message channelPost = update.channelPost();
        final boolean hasChannelPost = channelPost != null;
        if (hasChannelPost) {
            final Chat chat = channelPost.chat();
            final boolean isChannel = chat.isChannel();
            if (isChannel) {
                bot.getEventManager().call(new ChannelSendMessageEvent(bot, update, chat));
            }
        }
    }
}
