package org.teleight.teleightbots.conversation;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A conversation represents a structured interaction between a bot and a user.
 * <p>
 * A conversation typically follows a sequence of exchanges (messages) where the bot prompts
 * the user for input, processes the response, and decides on the next step.
 * <p>
 * A unique name identifies each conversation.
 *
 * @param name                   the name of the conversation, used to identify the conversation. Must be unique.
 * @param executor               the executor of the conversation, see {@link ConversationExecutor}
 * @param properties             the properties of the conversation
 * @param instanceConstraints    the constraints of the conversation instance, see {@link ConversationInstanceConstraints}
 * @param allowUnknownProperties whether unknown properties are allowed.
 *                               By default, unknown properties are not allowed and will throw an exception.
 * @see ConversationManager#registerConversation(Conversation)
 */
@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record Conversation(
        String name,
        ConversationExecutor executor,
        Map<String, Property<?>> properties,
        ConversationInstanceConstraints instanceConstraints,
        boolean allowUnknownProperties
) {

    public static Conversation.Builder ofBuilder(String name, ConversationExecutor executor) {
        return new Builder().name(name).executor(executor);
    }

    public static class Builder {
        Builder() {
            properties = new HashMap<>();
            instanceConstraints = ConversationInstanceConstraints.ofBuilder().build();
        }

        @lombok.experimental.Tolerate
        public Builder property(Property<?> property) {
            properties.put(property.name(), property);
            return this;
        }

        @lombok.experimental.Tolerate
        public Builder properties(List<Property<?>> properties) {
            properties.forEach(this::property);
            return this;
        }
    }

}
