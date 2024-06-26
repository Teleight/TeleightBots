package org.teleight.teleightbots.event.bot;

import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

public record BotShutdownEvent(
        TelegramBot bot
) implements Event {
}
