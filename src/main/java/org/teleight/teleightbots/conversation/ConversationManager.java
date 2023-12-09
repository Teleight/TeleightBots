package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface ConversationManager {

    default Conversation createConversation(String conversationName,
                                            Conversation.Executor executor) {
        return createConversation(conversationName, executor, 0, TimeUnit.MILLISECONDS);
    }

    @NotNull Conversation createConversation(String conversationName,
                                             Conversation.Executor executor,
                                             int conversationTimeout,
                                             TimeUnit conversationTimeoutUnit);

    void removeConversation(String conversationName);

    void joinConversation(User user, Chat chat, String conversationName);

    void leaveConversation(User user, String conversationName);

    boolean isUserInConversation(User user, String conversationName);

    @NotNull
    @Unmodifiable
    Collection<Conversation> getConversations();

    @NotNull
    Conversation getConversation(String conversationName);

    @NotNull
    @Unmodifiable
    Collection<RunningConversation> getRunningConversations();

}
