package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.methods.GetMe;
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
    default CompletableFuture<User> authenticate(TelegramBot bot) {
        return authenticate(bot, null);
    }

    @ApiStatus.Internal
    default CompletableFuture<User> authenticate(TelegramBot bot, @Nullable Consumer<Throwable> onFail) {
        return bot.execute(new GetMe())
                .whenComplete((user, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error while authenticating the bot: " + bot.getBotUsername());
                        if (bot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        if (onFail != null) {
                            onFail.accept(throwable);
                        }
                        bot.shutdown();
                        return;
                    }
                    System.out.println("Bot authenticated: " + user.username());
                });
    }

    @ApiStatus.Internal
    default CompletableFuture<UpdateReceivedEvent> handleNewUpdate(@NotNull TelegramBot bot, @NotNull Update update, @NotNull String responseJson) {
        final var eventFuture = bot.getEventManager().call(new UpdateReceivedEvent(bot, update, responseJson));
        eventFuture.thenAccept(event -> {
            final Update receivedUpdate = event.update();
            for (EventProcessor processorEvent : processorEvents) {
                processorEvent.processUpdate(bot, receivedUpdate);
            }
        });
        return eventFuture;
    }

}
