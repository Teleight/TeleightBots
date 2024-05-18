package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This is a class for a ConversationContext.
 * It provides methods to interact with the bot, chat, and the user in an active conversation
 *
 * @see Conversation
 * @see ConversationManager#registerConversation(Conversation)
 */
public class ConversationContext {

    // The bot associated with this ConversationContext
    private final Bot bot;
    // The chat associated with this ConversationContext
    private final Chat chat;
    // The user associated with this ConversationContext
    private final User user;
    // The conversation associated with this ConversationContext
    private final Conversation conversation;

    // The running conversation thread associated with this ConversationContext
    @ApiStatus.Internal
    private final RunningConversation runningConversation = new RunningConversation();

    // Queue to store updates received during the conversation
    private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>();

    /**
     * Constructs a new ConversationContext. Used internally and SHOULD NOT be used elsewhere
     *
     * @param bot          The bot associated with this conversation.
     * @param chat         The chat associated with this conversation.
     * @param user         The user involved in the conversation.
     * @param conversation The conversation to be executed.
     */
    @ApiStatus.Internal
    protected ConversationContext(@NotNull Bot bot,
                                  @NotNull Chat chat,
                                  @NotNull User user,
                                  @NotNull Conversation conversation) {
        this.bot = bot;
        this.chat = chat;
        this.user = user;
        this.conversation = conversation;

        // Add a listener to the bot's event manager to capture updates
        bot.getEventManager().addListener(UpdateReceivedEvent.class, event -> updateQueue.add(event.update()));

        // Schedule the conversation to run asynchronously
        runningConversation.setName(String.format("Conversation-%s-%s", conversation.name(), user.id()));
        bot.getScheduler().buildTask(runningConversation).schedule();
    }

    /**
     * @return The bot associated with this ConversationContext.
     */
    public Bot bot() {
        return bot;
    }

    /**
     * @return The chat associated with this ConversationContext.
     */
    public Chat chat() {
        return chat;
    }

    /**
     * @return The user associated with this ConversationContext.
     */
    public User user() {
        return user;
    }

    /**
     * Used internally and SHOULD NOT be used elsewhere
     * @return The running conversation thread associated with this ConversationContext.
     */
    @ApiStatus.Internal
    protected RunningConversation runningConversation() {
        return runningConversation;
    }

    /**
     * Waits for an update with no timeout. This is not recommended in most cases
     *
     * @return The update, or null if no update is received.
     * @deprecated use {@link ConversationContext#waitForUpdate(long, TimeUnit)}
     */
    @Deprecated
    public @Nullable Update waitForUpdate() {
        return waitForUpdate(0, TimeUnit.MILLISECONDS);
    }

    /**
     * Waits for an update with a specified timeout.
     *
     * @param timeout The timeout in the specified time unit.
     * @param unit    The time unit of the timeout.
     * @return The update, or null if no update is received within the timeout.
     */
    public @Nullable Update waitForUpdate(long timeout, @NotNull TimeUnit unit) {
        try {
            return updateQueue.poll(timeout, unit);
        } catch (InterruptedException e) {
            bot.getConversationManager().leaveConversation(user, conversation.name());
            return null;
        }
    }

    /**
     * This class represents a running conversation associated with a {@link ConversationContext}.
     * It is responsible for executing the conversation and managing its lifecycle.
     * This class is internal and SHOULD NOT be accessed elsewhere.
     */
    @ApiStatus.Internal
    protected class RunningConversation extends Thread {
        @Override
        public void run() {
            // Start the conversation
            conversation.execute(ConversationContext.this);
            // All work is done, leave the conversation
            bot.getConversationManager().leaveConversation(user, conversation.name());
        }
    }

}
