package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

/// Represents an executor for a conversation.
@FunctionalInterface
public interface ConversationExecutor {

    /// Executes the conversation flow.
    ///
    /// @param context the context of the currently running conversation
    void execute(@NotNull ConversationContext context);

}
