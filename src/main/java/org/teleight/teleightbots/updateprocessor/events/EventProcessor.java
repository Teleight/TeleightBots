package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.Bot;

public sealed interface EventProcessor permits
        CallbackQueryEventProcessor,
        ChannelPostEventProcessor,
        InlineQueryEventProcessor,
        MessageEventProcessor,
        MyChatMemberEventProcessor {

    void processUpdate(@NotNull Bot bot, @NotNull Update update);

}
