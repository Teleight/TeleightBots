package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public class RunningConversation extends Thread {

    private final Object lock = new Object();

    private final EventManager eventManager = new EventManagerImpl();

    private final long userId;
    private final ConversationImpl conversation;

    private final AtomicBoolean running = new AtomicBoolean(true);
    private Object result;
    private Class<? extends ApiResult> lastResultType;

    public RunningConversation(long userId, ConversationImpl conversation) {
        this.userId = userId;
        this.conversation = conversation;

        eventManager.addListener(UpdateReceivedEvent.class, event -> {
            if (event.update().getClass().equals(lastResultType)) {
                result = event;
                lock.notify();
            }
        });
    }

    @Override
    public void run() {
        while (running.get()) {
            // todo timeout
        }
    }

    public <T extends ApiResult> T waitFor(@NotNull Class<T> resultType) {
        return waitFor(resultType, 0, TimeUnit.MILLISECONDS);
    }

//    @Override
    public <T extends ApiResult> T waitFor(@NotNull Class<T> resultType, long timeout, @NotNull TimeUnit unit) {
        lastResultType = resultType;
        try {
            lock.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ((T) result);
    }
}
