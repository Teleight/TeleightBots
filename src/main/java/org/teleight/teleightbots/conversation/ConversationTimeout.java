package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

/**
 * Represents a timeout for a conversation.
 * <p>
 * The timeout is represented by a number and a {@link TimeUnit}.
 * </p>
 *
 * @param timeout  the number of units before the timeout (must be between 0 and 10_000)
 * @param timeUnit the unit of time for the timeout
 */
public record ConversationTimeout(
        @Range(from = 0, to = 10_000L)
        int timeout,

        @NotNull
        TimeUnit timeUnit
) {

}
