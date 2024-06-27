package org.teleight.teleightbots.event.user;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.InlineQuery;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

public record UserInlineQueryReceivedEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update
) implements Event {

    public @NotNull User user() {
        return update().inlineQuery().from();
    }

    public @NotNull InlineQuery inlineQuery() {
        return update().inlineQuery();
    }

}
