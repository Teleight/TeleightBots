package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

public interface Conversation {

    void execute(@NotNull ConversationContext context);

    @NotNull String name();

    @Range(from = 0, to = 10_000L) int conversationTimeout();

    @NotNull TimeUnit conversationTimeoutUnit();

}
