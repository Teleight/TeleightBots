package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;

public final class CallbackQueryEventProcessor implements EventProcessor {
    @Override
    public void processUpdate(@NotNull Bot bot, @NotNull Update update) {
        if (update.callbackQuery() == null) return;
        final ButtonPressEvent buttonPressEvent = new ButtonPressEvent(bot, update);
        bot.getMenuManager().getEventNode().call(buttonPressEvent);
    }
}
