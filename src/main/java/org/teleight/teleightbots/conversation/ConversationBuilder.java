package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface ConversationBuilder {

    default Conversation createConversation(@NotNull String conversationName,
                                            @NotNull ConversationExecutor executor) {
        return createConversation(conversationName, executor, 0, TimeUnit.MILLISECONDS);
    }

    Conversation createConversation(@NotNull String conversationName,
                                    @NotNull ConversationExecutor executor,
                                    long timeout,
                                    TimeUnit unit);

    class ConversationBuilderImpl implements ConversationBuilder {

        @Override
        public Conversation createConversation(@NotNull String conversationName,
                                               @NotNull ConversationExecutor executor,
                                               long timeout,
                                               TimeUnit unit) {
            return new ConversationImpl(conversationName, executor, timeout, unit);
        }
    }

}
