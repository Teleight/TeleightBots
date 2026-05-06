package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/// Represents the context of an active conversation between a bot and a user in a specific chat.
///
/// @see Conversation
/// @see ConversationManager#registerConversation(Conversation)
public interface ConversationContext {

    /// @return The bot associated with this ConversationContext.
    @NotNull TelegramBot bot();

    /// @return The chat associated with this ConversationContext.
    @NotNull Chat chat();

    /// @return The user associated with this ConversationContext.
    @NotNull User user();

    /// @return The conversation associated with this ConversationContext.
    @NotNull Conversation conversation();

    /// Gets a property by name.
    ///
    /// @param name The name of the property.
    /// @return The property, or null if the property does not exist.
    @Nullable Property<?> getProperty(@NotNull String name);

    /// @return A map of all properties applied to the conversation.
    @Unmodifiable @NotNull Map<String, Property<?>> getProperties();

    /// Waits for an update with no timeout. This is not recommended in most cases.
    ///
    /// @return The update, or null if no update is received.
    /// @deprecated use [ConversationContext#waitForUpdate(long, TimeUnit)]
    @Deprecated
    @Nullable Update waitForUpdate();

    /// Waits for an update with a specified timeout.
    ///
    /// @param timeout The timeout in the specified time unit.
    /// @param unit    The time unit of the timeout.
    /// @return The update, or null if no update is received within the timeout.
    @Nullable Update waitForUpdate(long timeout, @NotNull TimeUnit unit);

}
