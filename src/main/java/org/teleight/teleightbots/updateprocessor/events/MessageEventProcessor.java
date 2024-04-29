package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.bot.group.BotJoinedGroupEvent;
import org.teleight.teleightbots.event.user.UserMessageReceivedEvent;

public final class MessageEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull Bot bot, @NotNull Update update) {
        if (update.message() == null) return;

        final Message message = update.message();
        final User sender = message.from();
        final String messageText = message.text();

        final boolean hasText = messageText != null;
        final boolean hasSender = sender != null;
        final boolean hasFormat = message.forwardOrigin() == null;

        if (hasText && hasSender) {
            final boolean isPossibleCommand = messageText.startsWith("/");
            if (isPossibleCommand) {
                bot.getCommandManager().execute(sender, messageText, message);
            }
        }

        //Write
        if (hasText && hasFormat) {
            bot.getEventManager().call(new UserMessageReceivedEvent(bot, update));
        }

        //Bot join
        final boolean hasNewChatMembers = message.newChatMembers() != null;
        if (hasNewChatMembers) {
            boolean isThisBotJoined = isThisBotJoined(bot, message);
            Boolean groupChatCreated = message.groupChatCreated();
            if (isThisBotJoined || (groupChatCreated != null && groupChatCreated)) {
                bot.getEventManager().call(new BotJoinedGroupEvent(bot, update));
            }
        }
    }

    private static boolean isThisBotJoined(Bot bot, Message message) {
        for (User user : message.newChatMembers()) {
            if (user != null && user.username() != null && user.username().equalsIgnoreCase(bot.getBotUsername())) {
                return true;
            }
        }
        return false;
    }
}
