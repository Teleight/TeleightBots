package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
public class ConversationImpl implements Conversation {

    private final EventManager eventManager = new EventManagerImpl();

    private final String conversationName;
    private final Conversation.Executor executor;
    private final long conversationTimeout;
    private final TimeUnit conversationTimeoutUnit;

    private UpdateReceivedEvent lastUpdateReceivedEvent;

    public ConversationImpl(@NotNull String conversationName,
                            @NotNull Conversation.Executor executor,
                            long conversationTimeout,
                            @NotNull TimeUnit conversationTimeoutUnit) {
        this.conversationName = conversationName;
        this.executor = executor;
        this.conversationTimeout = conversationTimeout;
        this.conversationTimeoutUnit = conversationTimeoutUnit;

        eventManager.addListener(UpdateReceivedEvent.class, event -> {
            lastUpdateReceivedEvent = event;
        });
    }

    @Override
    public String getName() {
        return conversationName;
    }



}
