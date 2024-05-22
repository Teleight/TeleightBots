package org.teleight.teleightbots.event.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

public record UpdateReceivedEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update,
        @NotNull String json
) implements Event {

}
