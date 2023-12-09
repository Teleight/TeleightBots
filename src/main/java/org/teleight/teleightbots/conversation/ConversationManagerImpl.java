package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
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
    public void joinConversation(User user, Chat chat, String conversationName) {
        Conversation conversation = conversations.get(conversationName);
        if (usersInConversation.containsKey(user.id())) {
            throw new IllegalArgumentException("The user " + user.id() + " is already in a conversation");
        }
        if (conversation == null) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        RunningConversation runningConversation = new RunningConversation(bot, user, chat, (ConversationImpl) conversation);
        usersInConversation.put(user.id(), runningConversation);
        runningConversation.start();
    }

    @Override
    public void leaveConversation(User user, String conversationName) {
        final RunningConversation conversation = usersInConversation.get(user.id());
        if (conversation == null) {
            throw new IllegalArgumentException("The user " + user.id() + " is not in a conversation");
        }
        conversation.UNSAFE_stopConversation();
        usersInConversation.remove(user.id());
    }

    @Override
    public boolean isUserInConversation(User user, String conversationName) {
        return usersInConversation.containsKey(user.id());
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
