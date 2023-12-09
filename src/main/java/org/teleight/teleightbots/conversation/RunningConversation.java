package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public class RunningConversation extends Thread {

    private static final Scheduler SCHEDULER = Scheduler.newScheduler();

    // We need to synchronize on this object to wait for the next message
    private final Object lock = new Object();

    private final EventManager eventManager = new EventManagerImpl();

    private final Bot bot;
    private final long userId;
    private final ConversationImpl conversation;

    private Object result;
    private long resultMillis;
    private Class<? extends ApiResult> lastResultType;

    /**
     * This is used to acknowledge the first event received by the bot, which is the event that
     * triggered the bot to join the conversation.
     * <p>
     * This is used to prevent the bot from immediately responding to the first event.
     */
    private final AtomicBoolean acknowledgeFirstEvent = new AtomicBoolean(false);

    public RunningConversation(Bot bot, long userId, ConversationImpl conversation) {
        this.bot = bot;
        this.userId = userId;
        this.conversation = conversation;

        eventManager.addListener(UpdateReceivedEvent.class, event -> {
            synchronized (lock) {
                if (!acknowledgeFirstEvent.get()) {
                    acknowledgeFirstEvent.set(true);
                    return;
                }
                result = event.update().message();
                resultMillis = System.currentTimeMillis();
                lock.notify();
            }
        });

        SCHEDULER.buildTask(() -> {
            // Check if the conversation has timed out after the last message
            long currentMillis = System.currentTimeMillis();
            long conversationTimeoutMillis = conversation.getConversationTimeoutUnit().toMillis(conversation.getConversationTimeout());
            if (conversation.getConversationTimeout() > 0
                    && currentMillis - resultMillis > conversationTimeoutMillis) {
                // The conversation has timed out, leave the conversation
                bot.getConversationManager().leaveConversation(userId, conversation.getName());
            }
        }).repeat(10, TimeUnit.MILLISECONDS).schedule();
    }

    @Override
    public void run() {
        // Start the conversation
        conversation.getExecutor().execute(bot, this);
        // All work is done, leave the conversation
        bot.getConversationManager().leaveConversation(userId, conversation.getName());
    }

    public <T extends ApiResult> T waitFor(@NotNull Class<T> resultType) {
        return waitFor(resultType, 0, TimeUnit.MILLISECONDS);
    }

    public <T extends ApiResult> T waitFor(@NotNull Class<T> resultType, long timeout, @NotNull TimeUnit unit) {
        lastResultType = resultType;
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: Find a more robust way to do this
        // I mean, this is pretty bad
        return ((T) result);
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
