package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an executor for a conversation.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * Conversation testConversation = Conversation.ofBuilder("test", context -> {
 *      String id = context.chat().id();
 *      SendMessage message = SendMessage.ofBuilder(id, "Test conversation").build();
 *      context.bot().execute(message);
 * }).build();
 * }</pre>
 */
@FunctionalInterface
public interface ConversationExecutor {

    /**
     * Executes the conversation flow.
     *
     * @param context the context of the currently running conversation
     */
    void execute(@NotNull ConversationContext context);

}
