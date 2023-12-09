package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;

import java.util.concurrent.TimeUnit;

public interface Conversation {

    @NotNull
    String getName();

    @NotNull
    Conversation.Executor getExecutor();

    @Range(from = 0, to = 10_000L)
    int getConversationTimeout();

    @NotNull
    TimeUnit getConversationTimeoutUnit();

    @FunctionalInterface
    interface Executor {
        void execute(Bot bot, Chat chat, RunningConversation conversation);
    }
}
