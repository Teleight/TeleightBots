package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;

import java.util.Collection;

/**
 * This is an interface for a ConversationManager. It provides methods to manage conversations.
 * @see Bot#getConversationManager()
 */
public interface ConversationManager {

    /**
     * Registers a new Conversation.
     * @param conversation The Conversation to be registered.
     */
    void registerConversation(@NotNull Conversation conversation);

    /**
     * Removes a Conversation
     * @param conversationName The name of the Conversation to be removed.
     */
    void removeConversation(@NotNull String conversationName);

    /**
     * Allows a User to join a Conversation in a specific Chat.
     * @param user The User who is joining the Conversation.
     * @param chat The Chat where the Conversation is taking place.
     * @param conversationName The name of the Conversation to join.
     */
    void joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName);

    /**
     * Allows a User to forcefully leave a Conversation.
     * @param user The User who is leaving the Conversation.
     * @param conversationName The name of the Conversation to leave.
     */
    void leaveConversation(@NotNull User user, @NotNull String conversationName);

    /**
     * Checks if a User is in a Conversation.
     * @param user The User to check.
     * @param conversationName The name of the Conversation to check.
     * @return True if the User is in the Conversation, false otherwise.
     */
    boolean isUserInConversation(@NotNull User user, @NotNull String conversationName);

    /**
     * Returns a collection of all Conversations.
     *
     * @return A collection of all Conversations.
     */
    @Unmodifiable @NotNull Collection<Conversation> getConversations();

    /**
     * Returns a Conversation by its name.
     * @param conversationName The name of the Conversation to return.
     * @return The Conversation with the given name, or null if it does not exist.
     */
    @Nullable Conversation getConversation(@NotNull String conversationName);

    /**
     * Returns a collection of all running Conversations.
     *
     * @return A collection of all running Conversations.
     */
    @NotNull @Unmodifiable Collection<RunningConversation> getRunningConversations();

}
