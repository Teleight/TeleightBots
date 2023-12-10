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
    public @NotNull Conversation createConversation(@NotNull String conversationName,
                                                    Conversation.@NotNull Executor executor,
                                                    int conversationTimeout,
                                                    @NotNull TimeUnit conversationTimeoutUnit) {
        if (conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException(STR."The conversation \{conversationName} has already been registered");
        }
        final Conversation conversation = new ConversationImpl(conversationName, executor, conversationTimeout, conversationTimeoutUnit);
        conversations.put(conversationName, conversation);
        return conversation;
    }

    @Override
    public void removeConversation(@NotNull String conversationName) {
        if (!conversations.containsKey(conversationName)) {
            throw new IllegalArgumentException(STR."The conversation \{conversationName} has not been registered");
        }
        conversations.remove(conversationName);
    }

    @Override
    public void joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName) {
        Conversation conversation = conversations.get(conversationName);
        if (usersInConversation.containsKey(user.id())) {
            throw new IllegalArgumentException(STR."The user \{user.id()} is already in a conversation");
        }
        if (conversation == null) {
            throw new IllegalArgumentException(STR."The conversation \{conversationName} has not been registered");
        }
        RunningConversation runningConversation = new RunningConversation(bot, user, chat, conversation);
        usersInConversation.put(user.id(), runningConversation);
        runningConversation.start();
    }

    @Override
    public void leaveConversation(@NotNull User user, @NotNull String conversationName) {
        final RunningConversation conversation = usersInConversation.get(user.id());
        if (conversation == null) {
            throw new IllegalArgumentException(STR."The user \{user.id()} is not in a conversation");
        }
        conversation.UNSAFE_stopConversation();
        usersInConversation.remove(user.id());
    }

    @Override
    public boolean isUserInConversation(@NotNull User user, @NotNull String conversationName) {
        return usersInConversation.containsKey(user.id());
    }

    @Override
    public @NotNull @Unmodifiable Collection<Conversation> getConversations() {
        return conversations.values();
    }

    @Override
    public @NotNull Conversation getConversation(@NotNull String conversationName) {
        final Conversation conversation = conversations.get(conversationName);
        if (conversation == null) {
            throw new IllegalArgumentException(STR."The conversation \{conversationName} has not been registered");
        }
        return conversation;
    }

    @Override
    public @NotNull @Unmodifiable Collection<RunningConversation> getRunningConversations() {
        return usersInConversation.values();
    }

}
