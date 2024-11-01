package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.updateprocessor.events.CallbackQueryEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.ChannelPostEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.ChatMemberStatusEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.EventProcessor;
import org.teleight.teleightbots.updateprocessor.events.InlineQueryEventProcessor;
import org.teleight.teleightbots.updateprocessor.events.MessageEventProcessor;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public sealed interface UpdateProcessor extends Closeable permits
        LongPollingUpdateProcessor,
        WebhookUpdateProcessor {

    @NotNull CompletableFuture<User> start();

    EventProcessor[] processorEvents = new EventProcessor[] {
            new CallbackQueryEventProcessor(),
            new ChannelPostEventProcessor(),
            new InlineQueryEventProcessor(),
            new MessageEventProcessor(),
            new ChatMemberStatusEventProcessor()
    };

    @ApiStatus.Internal
    default CompletableFuture<UpdateReceivedEvent> handleNewUpdate(@NotNull TelegramBot bot, @NotNull Update update, @NotNull String responseJson) {
        final CompletableFuture<UpdateReceivedEvent> eventFuture = bot.getEventManager().call(new UpdateReceivedEvent(bot, update, responseJson));
        eventFuture.thenAccept(event -> {
            final Update receivedUpdate = event.update();
            for (EventProcessor processorEvent : processorEvents) {
                processorEvent.processUpdate(bot, receivedUpdate);
            }
        });
        return eventFuture;
    }

}
