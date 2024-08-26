package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.bot.group.BotJoinGroupEvent;
import org.teleight.teleightbots.event.bot.group.BotQuitGroupEvent;
import org.teleight.teleightbots.event.bot.group.SelfJoinGroupEvent;
import org.teleight.teleightbots.event.bot.group.SelfQuitGroupEvent;
import org.teleight.teleightbots.event.user.group.UserJoinGroupEvent;
import org.teleight.teleightbots.event.user.group.UserQuitGroupEvent;

public final class ChatMemberStatusEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull TelegramBot bot, @NotNull Update update) {
        final Message message = update.message();

        if (message == null) {
            return;
        }

        final User[] newChatMembers = message.newChatMembers();
        final User leftChatMember = message.leftChatMember();

        final Chat chat = message.chat();
        final boolean isGroup = chat.isGroup() || chat.isSuperGroup();

        if (!isGroup) return;

        if (newChatMembers != null) {
            handleNewChatMembers(bot, update, chat, newChatMembers);
        }
        if (leftChatMember != null) {
            handleLeftChatMember(bot, update, chat, leftChatMember);
        }
    }

    private void handleNewChatMembers(@NotNull TelegramBot bot, @NotNull Update update, @NotNull Chat chat, @NotNull User[] newChatMembers) {
        for (User newChatMember : newChatMembers) {
            final boolean isSelf = bot.getBotUsername().equals(newChatMember.username());
            final boolean isBot = newChatMember.isBot();
            if (isSelf) {
                bot.getEventManager().call(new BotJoinGroupEvent(bot, newChatMember, chat, update));
            } else if (isBot) {
                bot.getEventManager().call(new SelfJoinGroupEvent(bot, newChatMember, chat, update));
            } else {
                bot.getEventManager().call(new UserJoinGroupEvent(bot, newChatMember, chat, update));
            }
        }
    }

    private void handleLeftChatMember(@NotNull TelegramBot bot, @NotNull Update update, @NotNull Chat chat, @NotNull User leftChatMember) {
        final boolean isSelf = bot.getBotUsername().equals(leftChatMember.username());
        final boolean isBot = leftChatMember.isBot();
        if (isSelf) {
            bot.getEventManager().call(new BotQuitGroupEvent(bot, leftChatMember, chat, update));
        } else if (isBot) {
            bot.getEventManager().call(new SelfQuitGroupEvent(bot, leftChatMember, chat, update));
        } else {
            bot.getEventManager().call(new UserQuitGroupEvent(bot, leftChatMember, chat, update));
        }
    }
}
