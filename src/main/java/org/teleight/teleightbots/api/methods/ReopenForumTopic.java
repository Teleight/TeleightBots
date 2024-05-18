package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record ReopenForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        int messageThreadId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, int messageThreadId) {
        return new ReopenForumTopic.Builder(chatId, messageThreadId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "reopenForumTopic";
    }

    public static class Builder {
        private final String chatId;
        private final int messageThreadId;

        Builder(String chatId, int messageThreadId) {
            this.chatId = chatId;
            this.messageThreadId = messageThreadId;
        }

        public ReopenForumTopic build() {
            return new ReopenForumTopic(this.chatId, this.messageThreadId);
        }
    }
}
