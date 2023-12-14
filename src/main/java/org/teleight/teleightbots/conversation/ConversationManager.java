package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface ConversationManager {

    @NotNull
    default Conversation createConversation(@NotNull String conversationName,
                                            @NotNull Conversation.Executor executor) {
        return createConversation(conversationName, executor, 0, TimeUnit.MILLISECONDS);
    }

    @NotNull Conversation createConversation(@NotNull String conversationName,
                                             @NotNull Conversation.Executor executor,
                                             int conversationTimeout,
                                             @NotNull TimeUnit conversationTimeoutUnit);

    void removeConversation(@NotNull String conversationName);

    void joinConversation(@NotNull User user, @NotNull Chat chat, @NotNull String conversationName);

    void leaveConversation(@NotNull User user, @NotNull String conversationName);

    boolean isUserInConversation(@NotNull User user, @NotNull String conversationName);

    @NotNull
    @Unmodifiable
    Collection<Conversation> getConversations();

    @NotNull
    Conversation getConversation(@NotNull String conversationName);

    @NotNull
    @Unmodifiable
    Collection<RunningConversation> getRunningConversations();

}
