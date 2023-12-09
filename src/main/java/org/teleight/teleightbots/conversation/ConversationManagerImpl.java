package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.bot.Bot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConversationManagerImpl implements ConversationManager {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<Long, RunningConversation> usersInConversation = new HashMap<>();

    private final Bot bot;

    public ConversationManagerImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public @NotNull Conversation createConversation(String conversationName,
                                                    Conversation.Executor executor,
                                                    int conversationTimeout,
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
        if (usersInConversation.containsKey(userId)) {
            throw new IllegalArgumentException("The user " + userId + " is already in a conversation");
        }
        if (conversation == null) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        RunningConversation runningConversation = new RunningConversation(bot, userId, (ConversationImpl) conversation);
        usersInConversation.put(userId, runningConversation);
        runningConversation.start();
    }

    @Override
    public void leaveConversation(long userId, String conversationName) {
        final RunningConversation conversation = usersInConversation.get(userId);
        if (conversation == null) {
            throw new IllegalArgumentException("The user " + userId + " is not in a conversation");
        }
        conversation.UNSAFE_stopConversation();
        usersInConversation.remove(userId);
    }

    @Override
    public boolean isUserInConversation(long userId, String conversationName) {
        return usersInConversation.containsKey(userId);
    }

    @Override
    public @NotNull @Unmodifiable Collection<Conversation> getConversations() {
        return conversations.values();
    }

    @Override
    public @NotNull Conversation getConversation(String conversationName) {
        final Conversation conversation = conversations.get(conversationName);
        if (conversation == null) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        return conversation;
    }

    @Override
    public @NotNull @Unmodifiable Collection<RunningConversation> getRunningConversations() {
        return usersInConversation.values();
    }

}
