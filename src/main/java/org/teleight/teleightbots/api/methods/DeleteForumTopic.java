package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record DeleteForumTopic(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id", required = true)
        int messageThreadId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, int messageThreadId) {
        return new DeleteForumTopic.Builder(chatId, messageThreadId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteForumTopic";
    }

    public static class Builder {
        private final String chatId;
        private final int messageThreadId;

        Builder(String chatId, int messageThreadId) {
            this.chatId = chatId;
            this.messageThreadId = messageThreadId;
        }

        public DeleteForumTopic build() {
            return new DeleteForumTopic(this.chatId, this.messageThreadId);
        }
    }
}
