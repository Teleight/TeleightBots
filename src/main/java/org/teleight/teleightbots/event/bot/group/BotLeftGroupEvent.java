package org.teleight.teleightbots.event.bot.group;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.GroupBotEvent;

public record BotLeftGroupEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update
) implements GroupBotEvent {

}
