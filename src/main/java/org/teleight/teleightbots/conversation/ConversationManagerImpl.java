package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConversationManagerImpl implements ConversationManager {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<Long, RunningConversation> usersInConversation = new HashMap<>();

    @Override
    public @NotNull Conversation createConversation(String conversationName,
                                                    Conversation.Executor executor,
                                                    long conversationTimeout,
                                                    TimeUnit conversationTimeoutUnit) {
        if (conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has already been registered");
        }
        final Conversation conversation = new ConversationImpl(conversationName, executor, conversationTimeout, conversationTimeoutUnit);
        conversations.put(conversationName, conversation);
        return conversation;
    }

    @Override
    public void removeConversation(String conversationName) {
        if (!conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        conversations.remove(conversationName);
    }

    @Override
    public void joinConversation(long userId, String conversationName) {
        Conversation conversation = conversations.get(conversationName);
        if (!usersInConversation.containsKey(userId)) {
            throw new IllegalArgumentException("The user " + userId + " is already in a conversation");
        }
        if (!conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        usersInConversation.put(userId, new RunningConversation(userId, conversation));
    }

    @Override
    public void leaveConversation(long userId, String conversationName) {
        if (!usersInConversation.containsKey(userId)) {
            throw new IllegalArgumentException("The user " + userId + " is not in a conversation");
        }
        usersInConversation.remove(userId);
    }

    @Override
    public @NotNull @Unmodifiable Collection<Conversation> getConversations() {
        return conversations.values();
    }

    @Override
    public @NotNull Conversation getConversation(String conversationName) {
        if (!conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        return conversations.get(conversationName);
    }

}
