package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * This is a class for a ConversationContext. It provides methods to interact with a bot, a chat, and a running conversation.
 * @see Conversation
 * @see ConversationManager#registerConversation(Conversation)
 */
public final class ConversationContext {

    // The bot associated with this ConversationContext
    private final Bot bot;
    // The chat associated with this ConversationContext
    private final Chat chat;
    // The running conversation associated with this ConversationContext
    private final RunningConversation runningConversation;

    /**
     * This is the constructor for a ConversationContext.
     * @param bot The bot associated with this ConversationContext.
     * @param chat The chat associated with this ConversationContext.
     * @param runningConversation The running conversation associated with this ConversationContext.
     */
    public ConversationContext(@NotNull Bot bot, @NotNull Chat chat, RunningConversation runningConversation) {
        this.bot = bot;
        this.chat = chat;
        this.runningConversation = runningConversation;
    }

    /**
     * This method returns the bot associated with this ConversationContext.
     * @return The bot associated with this ConversationContext.
     */
    public Bot bot() {
        return bot;
    }

    /**
     * This method returns the chat associated with this ConversationContext.
     * @return The chat associated with this ConversationContext.
     */
    public Chat chat() {
        return chat;
    }

    /**
     * This method waits for an update with no timeout.
     * @return The update, or null if no update is received.
     */
    public @Nullable Update waitForUpdate() {
        return waitForUpdate(0, TimeUnit.MILLISECONDS);
    }

    /**
     * This method waits for an update with a specified timeout.
     * @param timeout The timeout in the specified time unit.
     * @param unit The time unit of the timeout.
     * @return The update, or null if no update is received within the timeout.
     */
    public @Nullable Update waitForUpdate(long timeout, @NotNull TimeUnit unit) {
        return runningConversation.waitForUpdate(timeout, unit);
    }

    /**
     * This method executes an API method.
     * @param method The API method to execute.
     * @param <R> The type of the result of the API method.
     * @return A CompletableFuture of the result of the API method.
     */
    public <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        return bot.execute(method);
    }

}
