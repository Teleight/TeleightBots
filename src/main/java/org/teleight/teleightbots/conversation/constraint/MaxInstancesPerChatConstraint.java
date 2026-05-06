package org.teleight.teleightbots.conversation.constraint;

import org.jetbrains.annotations.NotNullByDefault;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.ConversationContext;

import java.util.Collection;

@NotNullByDefault
public record MaxInstancesPerChatConstraint(int limit) implements ConversationInstanceConstraint {

    @Override
    public ConstraintResult canJoin(Conversation conversation,
                                    Chat chat,
                                    Collection<ConversationContext> runningConversations) {
        long instances = runningConversations.stream()
                .filter(ctx -> ctx.conversation().name().equals(conversation.name()) && ctx.chat().id().equals(chat.id()))
                .count();
        return instances < limit
                ? new ConstraintResult.Allowed()
                : new ConstraintResult.Denied("Maximum number of instances per chat (" + limit + ") reached");
    }
}

