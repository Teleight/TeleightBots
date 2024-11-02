package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

final class ConversationManagerImpl implements ConversationManager {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Map<Long, ConversationContext> usersInConversation = new HashMap<>();

    private final TelegramBot bot;

    ConversationManagerImpl(@NotNull TelegramBot bot) {
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
    public JoinResult joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName) {
        return joinConversation(user, chat, conversationName, null);
    }

    @Override
    public JoinResult joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName, @Nullable Map<String, Object> properties) {
        final long userId = user.id();
        final Conversation conversation = conversations.get(conversationName);
        if (usersInConversation.containsKey(userId)) {
            return new JoinResult.AlreadyInConversation();
        }
        if (conversation == null) {
            return new JoinResult.ConversationNotFound();
        }

        JoinResult instanceCheckResult = checkMaxInstances(conversation, conversationName, chat, user);
        if (instanceCheckResult instanceof JoinResult.InstanceConstraintReached) {
            return instanceCheckResult;
        }

        usersInConversation.put(userId, new ConversationContext(bot, chat, user, conversation, properties));
        return new JoinResult.Success();
    }

    private JoinResult checkMaxInstances(Conversation conversation, String conversationName, Chat chat, User user) {
        final long userId = user.id();
        final String chatId = chat.id();

        List<JoinResult> results = Arrays.asList(
                checkInstanceLimit("instance", conversation.instanceConstraints().maxInstances(),
                        (ctx) -> ctx.conversation().name().equals(conversationName)),

                checkInstanceLimit("user", conversation.instanceConstraints().maxInstancesPerUser(),
                        (ctx) -> ctx.conversation().name().equals(conversationName) && ctx.user().id() == userId),

                checkInstanceLimit("chat", conversation.instanceConstraints().maxInstancesPerChat(),
                        (ctx) -> ctx.conversation().name().equals(conversationName) && ctx.chat().id().equals(chatId)),

                checkInstanceLimit("user in chat", conversation.instanceConstraints().maxInstancesPerUserPerChat(),
                        (ctx) -> ctx.conversation().name().equals(conversationName) && ctx.user().id() == userId && ctx.chat().id().equals(chatId))
        );

        for (JoinResult result : results) {
            if (result instanceof JoinResult.InstanceConstraintReached) {
                return result;
            }
        }

        return new JoinResult.Success();
    }

    private JoinResult checkInstanceLimit(String context, int limit, Predicate<ConversationContext> predicate) {
        if (limit != -1) {
            long instances = usersInConversation.values().stream().filter(predicate).count();
            if (instances >= limit) {
                return new JoinResult.InstanceConstraintReached(context);
            }
        }
        return new JoinResult.Success();
    }

    @Override
    public void leaveConversation(@NotNull User user) {
        final long userId = user.id();
        final ConversationContext conversation = usersInConversation.get(userId);
        if (conversation == null) {
            throw new IllegalStateException("The user " + userId + " is not in a conversation");
        }
        conversation.runningConversation().interrupt();
        usersInConversation.remove(userId);
    }

    @Override
    public boolean isUserInConversation(@NotNull User user) {
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
