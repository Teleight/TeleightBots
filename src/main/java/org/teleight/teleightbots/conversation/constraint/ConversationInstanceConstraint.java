package org.teleight.teleightbots.conversation.constraint;

import org.jetbrains.annotations.NotNullByDefault;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.ConversationContext;

import java.util.Collection;

/**
 * Represents a constraint for determining whether a participant can join a specific conversation instance.
 */
@NotNullByDefault
public interface ConversationInstanceConstraint {

    /**
     * Determines whether a participant can join a specific conversation instance.
     *
     * @param conversation the conversation instance to check.
     * @param chat         the chat instance representing the context for the conversation.
     * @param runningConversations the currently active conversation contexts.
     * @return a {@link ConstraintResult} indicating whether the participant can join the conversation.
     */
    ConstraintResult canJoin(Conversation conversation,
                             Chat chat,
                             Collection<ConversationContext> runningConversations);

    /**
     * Represents the result of a constraint check.
     */
    sealed interface ConstraintResult permits ConstraintResult.Allowed, ConstraintResult.Denied {

        /**
         * Represents a passing constraint check.
         */
        record Allowed() implements ConstraintResult {}

        /**
         * Represents a constraint violation.
         * @param reason A human-readable reason describing why the constraint was not met.
         */
        record Denied(String reason) implements ConstraintResult {}
    }

}

