package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ReopenForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        int messageThreadId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, int messageThreadId) {
        return new ReopenForumTopic.Builder().chatId(chatId).messageThreadId(messageThreadId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "reopenForumTopic";
    }

}
