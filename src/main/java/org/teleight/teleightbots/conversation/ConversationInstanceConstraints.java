package org.teleight.teleightbots.conversation;

import lombok.Builder;

/**
 * Represents constraints for a conversation instance.
 * <p>
 * The constraints define the maximum number of instances that can be created for a conversation.
 *
 * @param maxInstances               the maximum number of instances that can be created for a conversation
 *                                   (default is -1, which means no limit)
 * @param maxInstancesPerUser        the maximum number of instances that can be created per user
 *                                   (default is -1, which means no limit)
 * @param maxInstancesPerChat        the maximum number of instances that can be created per chat
 *                                   (default is -1, which means no limit)
 * @param maxInstancesPerUserPerChat the maximum number of instances that can be created per user in a chat
 *                                   (default is -1, which means no limit)
 */
@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record ConversationInstanceConstraints(
        int maxInstances,
        int maxInstancesPerUser,
        int maxInstancesPerChat,
        int maxInstancesPerUserPerChat
) {

    public static class Builder {
        Builder() {
            maxInstances = -1;
            maxInstancesPerUser = -1;
            maxInstancesPerChat = -1;
            maxInstancesPerUserPerChat = -1;
        }
    }

}
