package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.scheduler.Task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ApiStatus.Internal
public class RunningConversation extends Thread {

    private static final Scheduler SCHEDULER = Scheduler.newScheduler();

    // We need to synchronize on this object to wait for the next message
    private final Object lock = new Object();

    private final EventManager eventManager = new EventManagerImpl();

    private final Bot bot;
    private final User user;
    private final Chat chat;
    private final Conversation conversation;
    private final long conversationTimeoutMillis;

    private Update result;
    private long lastUpdateMillis = System.currentTimeMillis();


    /**
     * This is used to stop the conversation when the conversation has timed out.
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * We need to keep a reference to the timeout task, so we can cancel it when the conversation
     */
    private final Task timeoutTask;

    public RunningConversation(@NotNull Bot bot,
                               @NotNull User user,
                               @NotNull Chat chat,
                               @NotNull Conversation conversation) {
        this.bot = bot;
        this.user = user;
        this.chat = chat;
        this.conversation = conversation;
        this.conversationTimeoutMillis = conversation.getConversationTimeoutUnit().toMillis(conversation.getConversationTimeout());

        setName(String.format("Conversation-%s-%s", conversation.getName(), user.id()));

        eventManager.addListener(UpdateReceivedEvent.class, event -> {
            synchronized (lock) {
                result = event.update();
                lastUpdateMillis = System.currentTimeMillis();
                lock.notify();
            }
        });

        timeoutTask = SCHEDULER.buildTask(() -> {
            // Check if the conversation has timed out after the last message
            long currentMillis = System.currentTimeMillis();
            if (conversation.getConversationTimeout() > 0 &&
                    lastUpdateMillis + conversationTimeoutMillis < currentMillis
            ) {
                // The conversation has timed out, leave the conversation
                bot.getConversationManager().leaveConversation(user, conversation.getName());
            }
        }).repeat(10, TimeUnit.MILLISECONDS).schedule();
    }

    @Override
    public void run() {
        running.set(true);
        // Start the conversation
        conversation.getExecutor().execute(bot, chat, this);
        // All work is done, leave the conversation
        if (running.get()) {
            bot.getConversationManager().leaveConversation(user, conversation.getName());
        }
    }

    @Nullable
    public Update waitForUpdate() {
        return waitForUpdate(0, TimeUnit.MILLISECONDS);
    }

    @Nullable
    public Update waitForUpdate(long timeout, @NotNull TimeUnit unit) {
        synchronized (lock) {
            try {
                long timeoutMillis = unit.toMillis(timeout);
                lock.wait(timeoutMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (result == null) {
            return null;
        }

        // Found the result, save it to a temporary variable, so we can reset the result to null
        var tmpResult = result;

        // Reset the result to null. This is done so that the next waitFor call will not return the same result
        this.result = null;

        return tmpResult;
    }

    @ApiStatus.Internal
    void UNSAFE_stopConversation() {
        running.set(false);
        timeoutTask.cancel();
        interrupt();
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
