package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConversationManagerImpl implements ConversationManager {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<Long, RunningConversation> usersInConversation = new HashMap<>();

    private final Bot bot;

    public ConversationManagerImpl(@NotNull Bot bot) {
        this.bot = bot;
    }

    @Override
    public synchronized void registerConversation(@NotNull Conversation conversation) {
        if (conversations.containsKey(conversation.name())) {
            throw new IllegalArgumentException("The conversation " + conversation.name() + " has already been registered");
        }
        conversations.put(conversation.name(), conversation);
    }

    @Override
    public void removeConversation(@NotNull String conversationName) {
        if (!conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        conversations.remove(conversationName);
    }

    @Override
    public void joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName) {
        var conversation = conversations.get(conversationName);
        if (usersInConversation.containsKey(user.id())) {
            throw new IllegalArgumentException("The user " + user.id() + " is already in a conversation");
        }
        if (conversation == null) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        var runningConversation = new RunningConversation(bot, user, chat, conversation);
        usersInConversation.put(user.id(), runningConversation);
        runningConversation.start();
    }

    @Override
    public void leaveConversation(@NotNull User user, @NotNull String conversationName) {
        final RunningConversation conversation = usersInConversation.get(user.id());
        if (conversation == null) {
            throw new IllegalArgumentException("The user " + user.id() + " is not in a conversation");
        }
        conversation.UNSAFE_stopConversation();
        usersInConversation.remove(user.id());
    }

    @Override
    public boolean isUserInConversation(@NotNull User user, @NotNull String conversationName) {
        return usersInConversation.containsKey(user.id());
    }

    @Override
    public @Unmodifiable @NotNull Collection<Conversation> getConversations() {
        return Collections.unmodifiableCollection(conversations.values());
    }

    @Override
    public @NotNull Conversation getConversation(@NotNull String conversationName) {
        return conversations.get(conversationName);
    }

    @Override
    public @NotNull @Unmodifiable Collection<RunningConversation> getRunningConversations() {
        return usersInConversation.values();
    }

}
