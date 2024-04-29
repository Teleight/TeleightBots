package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.user.UserInlineQueryReceivedEvent;

public final class InlineQueryEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull Bot bot, @NotNull Update update) {
        if (update.inlineQuery() != null) {
            bot.getEventManager().call(new UserInlineQueryReceivedEvent(bot, update));
        }
    }
}
