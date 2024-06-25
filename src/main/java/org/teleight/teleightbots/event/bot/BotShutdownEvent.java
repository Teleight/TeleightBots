package org.teleight.teleightbots.event.bot;

import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

public record BotShutdownEvent(
        Bot bot
) implements Event {
}
