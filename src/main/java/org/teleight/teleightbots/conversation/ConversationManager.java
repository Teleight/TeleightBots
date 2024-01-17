package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Collection;

public interface ConversationManager {

    void registerConversation(@NotNull Conversation conversation);

    void removeConversation(@NotNull String conversationName);

    void joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName);

    void leaveConversation(@NotNull User user, @NotNull String conversationName);

    boolean isUserInConversation(@NotNull User user, @NotNull String conversationName);

    @Unmodifiable @NotNull Collection<Conversation> getConversations();

    @Nullable Conversation getConversation(@NotNull String conversationName);

    @NotNull @Unmodifiable Collection<RunningConversation> getRunningConversations();

}
