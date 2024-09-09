package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Represents the context of an active conversation between a bot and a user in a specific chat.
 * <p>
 * This class provides mechanisms to manage the conversation's lifecycle, fetch custom properties,
 * interact with the bot, and capture updates from the user during the conversation.
 *
 * @see Conversation
 * @see ConversationManager#registerConversation(Conversation)
 */
public class ConversationContext {

    // The bot associated with this ConversationContext
    private final TelegramBot bot;
    // The chat associated with this ConversationContext
    private final Chat chat;
    // The user associated with this ConversationContext
    private final User user;
    // The conversation associated with this ConversationContext
    private final Conversation conversation;

    // The running conversation thread associated with this ConversationContext
    @ApiStatus.Internal
    private final ConversationLifecycle conversationLifecycle = new ConversationLifecycle();

    // Queue to store updates received during the conversation
    @ApiStatus.Internal
    private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>();

    // Map to store the properties applied to the conversation
    @ApiStatus.Internal
    private final Map<String, Property<?>> appliedProperties = new HashMap<>();

    /**
     * Constructs a new ConversationContext. Used internally and SHOULD NOT be used elsewhere
     *
     * @param bot          The bot associated with this conversation.
     * @param chat         The chat associated with this conversation.
     * @param user         The user involved in the conversation.
     * @param conversation The conversation to be executed.
     */
    @ApiStatus.Internal
    protected ConversationContext(@NotNull TelegramBot bot,
                                  @NotNull Chat chat,
                                  @NotNull User user,
                                  @NotNull Conversation conversation,
                                  @Nullable Map<String, Object> properties) {
        this.bot = bot;
        this.chat = chat;
        this.user = user;
        this.conversation = conversation;

        applyProperties(properties);

        // Add a listener to the bot's event manager to capture updates
        bot.getEventManager().addListener(UpdateReceivedEvent.class, event -> updateQueue.add(event.update()));

        // Schedule the conversation to run asynchronously
        conversationLifecycle.setName(String.format("Conversation-%s-%s", conversation.name(), user.id()));
        bot.getScheduler().buildTask(conversationLifecycle).schedule();
    }

    private void applyProperties(@Nullable Map<String, Object> properties) {
        // Apply the properties registered with the conversation
        conversation.properties().forEach((name, property) -> {
            if (properties != null && properties.containsKey(name)) {
                applyProperty(name, property.value());
            } else if (property.required()) {
                throw new IllegalArgumentException("The conversation " + conversation.name() + " requires a non-null value for the property " + property.name());
            }
        });

        // Apply the properties provided by the user
        if (properties != null) {
            properties.forEach((name, value) -> {
                if (!conversation.properties().containsKey(name)) {
                    if (conversation.allowUnknownProperties()) {
                        applyProperty(name, value);
                    } else {
                        throw new IllegalArgumentException("The conversation " + conversation.name() + " does not have a property named " + name);
                    }
                }
            });
        }
    }

    private void applyProperty(@NotNull String name, @NotNull Object value) {
        appliedProperties.put(name, Property.of(name, value));
    }

    /**
     * Gets a property by name.
     *
     * @param name The name of the property.
     * @return The property, or null if the property does not exist.
     */
    public @Nullable Property<?> getProperty(@NotNull String name) {
        return appliedProperties.get(name);
    }

    /**
     * @return The bot associated with this ConversationContext.
     */
    public TelegramBot bot() {
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
     * @return The conversation associated with this ConversationContext.
     */
    public Conversation conversation() {
        return conversation;
    }

    /**
     * Used internally and SHOULD NOT be used elsewhere
     * @return The running conversation thread associated with this ConversationContext.
     */
    @ApiStatus.Internal
    protected ConversationLifecycle runningConversation() {
        return conversationLifecycle;
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
            bot.getConversationManager().leaveConversation(user);
            return null;
        }
    }

    /**
     * This class represents a running conversation associated with a {@link ConversationContext}.
     * It is responsible for executing the conversation and managing its lifecycle.
     * This class is internal and SHOULD NOT be accessed elsewhere.
     */
    @ApiStatus.Internal
    protected class ConversationLifecycle extends Thread {
        @Override
        public void run() {
            // Start the conversation
            conversation.executor().execute(ConversationContext.this);
            // All work is done, leave the conversation
            bot.getConversationManager().leaveConversation(user);

            // Clear the update queue
            updateQueue.clear();
            // Clear the applied properties
            appliedProperties.clear();
        }
    }

}
