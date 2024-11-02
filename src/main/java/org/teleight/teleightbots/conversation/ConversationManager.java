package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.util.Collection;
import java.util.Map;

/**
 * This is an interface for a ConversationManager. It provides methods to manage conversations.
 *
 * @see TelegramBot#getConversationManager()
 */
public sealed interface ConversationManager permits ConversationManagerImpl {

    @ApiStatus.Internal
    static @NotNull ConversationManager newConversationManager(@NotNull TelegramBot bot) {
        return new ConversationManagerImpl(bot);
    }

    /**
     * Registers a new conversation.
     *
     * @param conversation The Conversation to be registered.
     *                     It should be a valid, non-null instance implementing the {@link Conversation} interface.
     * @throws NullPointerException if the conversation is null.
     */
    void registerConversation(@NotNull Conversation conversation);

    /**
     * Removes an existing Conversation.
     *
     * @param conversationName The name of the Conversation to be removed.
     *                         This should match the name returned by {@link Conversation#name()}.
     * @throws NullPointerException     if the conversation name is null.
     * @throws IllegalArgumentException if the conversation does not exist.
     */
    void removeConversation(@NotNull String conversationName);

    /**
     * Allows a user to join a registered conversation in a specified chat.
     *
     * @param user             The User who is joining the Conversation.
     * @param chat             The Chat where the conversation is taking place.
     * @param conversationName The name of the Conversation the user wants to join.
     *                         This should match the name returned by {@link Conversation#name()}.
     * @return A {@link JoinResult} indicating the result of the join operation.
     */
    JoinResult joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName);

    /**
     * Allows a user to join a registered conversation in a specified chat with custom properties.
     *
     * @param user             The User who is joining the Conversation.
     * @param chat             The Chat where the conversation is taking place.
     * @param conversationName The name of the Conversation the user wants to join.
     *                         This should match the name returned by {@link Conversation#name()}.
     * @param properties       A list of custom properties to be passed to the conversation.
     *                         This can be used to pass additional information to the conversation.
     * @return A {@link JoinResult} indicating the result of the join operation.
     */
    JoinResult joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName, @Nullable Map<String, Object> properties);

    /**
     * Allows a user to forcefully leave an active conversation.
     * This can be useful in cases where the bot needs to remove the user from the conversation for any reason.
     *
     * @param user The User who is leaving the conversation.
     * @throws IllegalStateException if the user is not in a conversation.
     */
    void leaveConversation(@NotNull User user);

    /**
     * Checks whether a user is currently participating in a specific conversation.
     *
     * @param user The user to check.
     * @return True if the user is currently part of the specified conversation, false otherwise.
     */
    boolean isUserInConversation(@NotNull User user);

    /**
     * Returns an unmodifiable collection of all registered conversations.
     * This collection includes all conversations that have been registered,
     * regardless of whether they are currently active.
     *
     * @return An unmodifiable collection of all registered Conversations.
     */
    @NotNull
    @Unmodifiable
    Collection<Conversation> getConversations();

    /**
     * Retrieves a specific conversation by its name.
     *
     * @param conversationName The name of the Conversation to retrieve.
     *                         This should match the name returned by {@link Conversation#name()}.
     * @return The Conversation corresponding to the given name,
     * or null if no such conversation is registered.
     */
    @Nullable Conversation getConversation(@NotNull String conversationName);

    /**
     * Returns an unmodifiable collection of all currently active conversations.
     *
     * @return An unmodifiable collection of all running ConversationContexts, representing
     * the active instances of conversations.
     */
    @NotNull
    @Unmodifiable
    Collection<ConversationContext> getRunningConversations();

    /**
     * Represents the result of a join operation.
     */
    sealed interface JoinResult permits
            JoinResult.AlreadyInConversation,
            JoinResult.ConversationNotFound,
            JoinResult.InstanceConstraintReached,
            JoinResult.Success {

        /**
         * Represents a successful join operation.
         */
        record Success() implements JoinResult {
        }

        /**
         * Represents a failed join operation due to the user already being in a conversation.
         */
        record AlreadyInConversation() implements JoinResult {
        }

        /**
         * Represents a failed join operation due to the conversation not being found.
         */
        record ConversationNotFound() implements JoinResult {
        }

        /**
         * Represents a failed join operation due to the maximum instance constraint being reached.
         */
        record InstanceConstraintReached(String constraint) implements JoinResult {
        }
    }
}
