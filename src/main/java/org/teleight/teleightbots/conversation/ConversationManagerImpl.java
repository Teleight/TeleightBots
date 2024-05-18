package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ConversationManagerImpl implements ConversationManager {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<Long, ConversationContext> usersInConversation = new HashMap<>();

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
        final long userId = user.id();
        final Conversation conversation = conversations.get(conversationName);
        if (usersInConversation.containsKey(userId)) {
            throw new IllegalArgumentException("The user " + userId + " is already in a conversation");
        }
        if (conversation == null) {
            throw new IllegalArgumentException("The conversation " + conversationName + " has not been registered");
        }
        usersInConversation.put(userId, new ConversationContext(bot, chat, user, conversation));
    }

    @Override
    public void leaveConversation(@NotNull User user, @NotNull String conversationName) {
        final long userId = user.id();
        final ConversationContext conversation = usersInConversation.get(userId);
        if (conversation == null) {
            throw new IllegalArgumentException("The user " + userId + " is not in a conversation");
        }
        conversation.runningConversation().interrupt();
        usersInConversation.remove(userId);
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
    public @NotNull @Unmodifiable Collection<ConversationContext> getRunningConversations() {
        return usersInConversation.values();
    }

}
