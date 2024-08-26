package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.user.UserMessageReceivedEvent;

public final class MessageEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull TelegramBot bot, @NotNull Update update) {
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
    }
}
