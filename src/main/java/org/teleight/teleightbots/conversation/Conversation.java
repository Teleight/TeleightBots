package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

/**
 * This is an interface for a Conversation. It provides methods to execute a conversation, get its name, and get its timeout.
 *
 * @see ConversationManager#registerConversation(Conversation)
 */
public interface Conversation {

    /**
     * This method executes a Conversation with a given context.
     *
     * @param context The context in which the Conversation is executed.
     */
    void execute(@NotNull ConversationContext context);

    /**
     * This method returns the name of the Conversation.
     *
     * @return The name of the Conversation.
     */
    @NotNull String name();

    /**
     * This method returns the timeout of the Conversation.
     *
     * @return The timeout of the Conversation.
     */
    @NotNull ConversationTimeout conversationTimeout();

}
