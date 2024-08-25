package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code Conversation} interface represents a structured interaction between a bot and a user.
 * <p>
 * A conversation typically follows a sequence of exchanges (messages) where the bot prompts
 * the user for input, processes the response, and decides on the next step.
 * <p>
 * A unique name identifies each conversation.
 *
 * @see ConversationManager#registerConversation(Conversation)
 */
public interface Conversation {

    /**
     * Executes the conversation logic within the provided context.
     *
     * @param context The context in which the conversation is executed.
     */
    void execute(@NotNull ConversationContext context);

    /**
     * Returns the unique name of the conversation.
     * This name is used to identify the conversation and should be descriptive of its purpose or function.
     *
     * @return The name of the conversation.
     */
    @NotNull String name();

}
