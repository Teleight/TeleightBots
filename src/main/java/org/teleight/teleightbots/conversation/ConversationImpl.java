package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
public record ConversationImpl(
        @NotNull
        String conversationName,

        @NotNull
        Conversation.Executor executor,

        @Range(from = 0, to = 10_000L)
        int conversationTimeout,

        @NotNull
        TimeUnit conversationTimeoutUnit
) implements Conversation {

    @Override
    public @NotNull String getName() {
        return conversationName;
    }

    @Override
    public @NotNull Conversation.Executor getExecutor() {
        return executor;
    }

    @Override
    public @Range(from = 0, to = 10_000L) int getConversationTimeout() {
        return conversationTimeout;
    }

    @Override
    public @NotNull TimeUnit getConversationTimeoutUnit() {
        return conversationTimeoutUnit;
    }

}
