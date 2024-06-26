package org.teleight.teleightbots.updateprocessor.events;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.TelegramBot;

public sealed interface EventProcessor permits
        CallbackQueryEventProcessor,
        ChannelPostEventProcessor,
        InlineQueryEventProcessor,
        MessageEventProcessor,
        MyChatMemberEventProcessor {

    void processUpdate(@NotNull TelegramBot bot, @NotNull Update update);

}
