package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface ConversationManager {

    default Conversation createConversation(String conversationName,
                                            Conversation.Executor executor) {
        return createConversation(conversationName, executor, 0, TimeUnit.MILLISECONDS);
    }

    @NotNull Conversation createConversation(String conversationName,
                                             Conversation.Executor executor,
                                             long conversationTimeout,
                                             TimeUnit conversationTimeoutUnit);

    void removeConversation(String conversationName);

    void joinConversation(long userId, String conversationName);

    void leaveConversation(long userId, String conversationName);

    @NotNull
    @Unmodifiable
    Collection<Conversation> getConversations();

    @NotNull
    Conversation getConversation(String conversationName);

    @NotNull
    @Unmodifiable
    Collection<RunningConversation> getRunningConversations();

}
