package org.teleight.teleightbots.event.bot.group;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.GroupBotEvent;

public record SelfJoinGroupEvent(
        @NotNull TelegramBot bot,
        @NotNull User user,
        @NotNull Chat chat,
        @NotNull Update update
) implements GroupBotEvent {

}
