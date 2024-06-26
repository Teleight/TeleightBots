package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record BatchDeleteMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, long[] messageIds) {
        return new BatchDeleteMessages.Builder(chatId, messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessages";
    }

    public static class Builder {
        private final String chatId;
        private final long[] messageIds;

        public Builder(String chatId, long[] messageIds) {
            this.chatId = chatId;
            this.messageIds = messageIds;
        }

        public BatchDeleteMessages build() {
            return new BatchDeleteMessages(this.chatId, this.messageIds);
        }
    }

}
