package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
public class ConversationContextImpl implements ConversationContext {

    private final TelegramBot bot;
    private final Chat chat;
    private final User user;
    private final Conversation conversation;

    // The running conversation thread associated with this ConversationContext
    private final ConversationLifecycle conversationLifecycle = new ConversationLifecycle();

    // Queue to store updates received during the conversation
    private final BlockingQueue<Update> updateQueue = new LinkedBlockingQueue<>();

    // Map to store the properties applied to the conversation
    private final Map<String, Property<?>> appliedProperties = new HashMap<>();

    protected ConversationContextImpl(@NotNull TelegramBot bot,
                                      @NotNull Chat chat,
                                      @NotNull User user,
                                      @NotNull Conversation conversation,
                                      @Nullable Map<String, Object> properties) {
        this.bot = bot;
        this.chat = chat;
        this.user = user;
        this.conversation = conversation;

        applyProperties(properties);

        bot.getEventManager().addListener(UpdateReceivedEvent.class, event -> updateQueue.add(event.update()));

        conversationLifecycle.setName(String.format("Conversation-%s-%s", conversation.name(), user.id()));
        bot.getScheduler().buildTask(conversationLifecycle).schedule();
    }

    private void applyProperties(@Nullable Map<String, Object> properties) {
        // Apply the properties registered with the conversation
        conversation.properties().forEach((name, property) -> {
            if (properties != null && properties.containsKey(name)) {
                applyProperty(name, properties.get(name));
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

    @Override
    public @Nullable Property<?> getProperty(@NotNull String name) {
        return appliedProperties.get(name);
    }

    @Override
    public @Unmodifiable @NotNull Map<String, Property<?>> getProperties() {
        return Collections.unmodifiableMap(appliedProperties);
    }

    @Override
    public @NotNull TelegramBot bot() {
        return bot;
    }

    @Override
    public @NotNull Chat chat() {
        return chat;
    }

    @Override
    public @NotNull User user() {
        return user;
    }

    @Override
    public @NotNull Conversation conversation() {
        return conversation;
    }

    @Override
    public @Nullable Update waitForUpdate() {
        return waitForUpdate(0, TimeUnit.MILLISECONDS);
    }

    @Override
    public @Nullable Update waitForUpdate(long timeout, @NotNull TimeUnit unit) {
        try {
            return updateQueue.poll(timeout, unit);
        } catch (InterruptedException e) {
            bot.getConversationManager().leaveConversation(user);
            return null;
        }
    }

    protected ConversationLifecycle runningConversation() {
        return conversationLifecycle;
    }

    /**
     * This class represents a running conversation associated with a {@link ConversationContextImpl}.
     * It is responsible for executing the conversation and managing its lifecycle.
     * This class is internal and SHOULD NOT be accessed elsewhere.
     */
    @ApiStatus.Internal
    protected class ConversationLifecycle extends Thread {
        @Override
        public void run() {
            // Start the conversation
            conversation.executor().execute(ConversationContextImpl.this);
            // All work is done, leave the conversation
            bot.getConversationManager().leaveConversation(user);

            updateQueue.clear();
            appliedProperties.clear();
        }
    }

}
