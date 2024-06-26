package org.teleight.teleightbots.event.user;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

public record UserInlineQueryReceivedEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update
) implements Event {

}
