package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.api.objects.ChatMemberAdministrator;
import org.teleight.teleightbots.api.objects.ChatMemberLeft;
import org.teleight.teleightbots.api.objects.ChatMemberMember;
import org.teleight.teleightbots.api.objects.ChatMemberRestricted;
import org.teleight.teleightbots.api.objects.ChatMemberUpdated;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.bot.channel.BotJoinChannelEvent;
import org.teleight.teleightbots.event.bot.channel.BotQuitChannelEvent;
import org.teleight.teleightbots.event.bot.group.BotJoinedGroupEvent;
import org.teleight.teleightbots.event.bot.group.BotLeftGroupEvent;

public final class MyChatMemberEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull Bot bot, @NotNull Update update) {
        final ChatMemberUpdated myChatMember = update.myChatMember();
        final boolean hasMyChatMember = myChatMember != null;

        if (!hasMyChatMember) {
            return;
        }

        final ChatMember newChatMember = myChatMember.newChatMember();
        final boolean isThisBot = bot.getBotUsername().equals(newChatMember.user().username());

        if (!isThisBot) {
            return;
        }

        final boolean isJoined = newChatMember instanceof ChatMemberAdministrator || newChatMember instanceof ChatMemberMember;
        final boolean isLeft = newChatMember instanceof ChatMemberLeft || newChatMember instanceof ChatMemberRestricted;

        final Chat chat = myChatMember.chat();
        final boolean isChannel = chat.isChannel();

        if (isJoined) {
            if (isChannel) {
                bot.getEventManager().call(new BotJoinChannelEvent(bot, update));
            } else {
                bot.getEventManager().call(new BotJoinedGroupEvent(bot, update));
            }
        }

        if (isLeft) {
            if (isChannel) {
                bot.getEventManager().call(new BotQuitChannelEvent(bot, update));
            } else {
                bot.getEventManager().call(new BotLeftGroupEvent(bot, update));
            }
        }
    }
}
