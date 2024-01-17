package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

public interface Conversation {

    void execute(@NotNull ConversationContext context);

    @NotNull String name();

    @NotNull ConversationTimeout conversationTimeout();

}
