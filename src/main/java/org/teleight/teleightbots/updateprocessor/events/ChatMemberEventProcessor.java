package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.bot.channel.BotJoinChannelEvent;
import org.teleight.teleightbots.event.bot.channel.BotQuitChannelEvent;
import org.teleight.teleightbots.event.bot.group.BotJoinGroupEvent;
import org.teleight.teleightbots.event.bot.group.BotQuitGroupEvent;
import org.teleight.teleightbots.event.user.channel.UserJoinChannelEvent;
import org.teleight.teleightbots.event.user.channel.UserQuitChannelEvent;
import org.teleight.teleightbots.event.user.group.UserJoinGroupEvent;
import org.teleight.teleightbots.event.user.group.UserQuitGroupEvent;

public final class ChatMemberEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull TelegramBot bot, @NotNull Update update) {
        final Message message = update.message();

        if (message == null) {
            return;
        }

        final User[] newChatMembers = message.newChatMembers();
        final User leftChatMember = message.leftChatMember();

        final Chat chat = message.chat();

        if (newChatMembers != null) {
            handleNewChatMembers(bot, update, chat, newChatMembers);
        }
        if (leftChatMember != null) {
            handleLeftChatMember(bot, update, chat, leftChatMember);
        }
    }

    private void handleNewChatMembers(@NotNull TelegramBot bot, @NotNull Update update, @NotNull Chat chat, @NotNull User[] newChatMembers) {
        final boolean isChannel = chat.isChannel();

        for (User newChatMember : newChatMembers) {
            final boolean isThisBot = bot.getBotUsername().equals(newChatMember.username());
            if (isThisBot) {
                if (isChannel) {
                    bot.getEventManager().call(new BotJoinChannelEvent(bot, update));
                } else {
                    bot.getEventManager().call(new BotJoinGroupEvent(bot, update));
                }
            } else {
                if (isChannel) {
                    bot.getEventManager().call(new UserJoinChannelEvent(bot, newChatMember, chat, update));
                } else {
                    bot.getEventManager().call(new UserJoinGroupEvent(bot, newChatMember, chat, update));
                }
            }
        }
    }

    private void handleLeftChatMember(@NotNull TelegramBot bot, @NotNull Update update, @NotNull Chat chat, @NotNull User leftChatMember) {
        final boolean isChannel = chat.isChannel();
        final boolean isThisBot = bot.getBotUsername().equals(leftChatMember.username());

        if (isThisBot) {
            if (isChannel) {
                bot.getEventManager().call(new BotQuitChannelEvent(bot, update));
            } else {
                bot.getEventManager().call(new BotQuitGroupEvent(bot, update));
            }
        } else {
            if (isChannel) {
                bot.getEventManager().call(new UserQuitChannelEvent(bot, leftChatMember, chat, update));
            } else {
                bot.getEventManager().call(new UserQuitGroupEvent(bot, leftChatMember, chat, update));
            }
        }
    }
}
