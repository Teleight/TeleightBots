package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

public record BatchForwardMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds
) implements ApiMethodMessage {

    public static Builder ofBuilder(String chatId, String fromChatId, long[] messageIds) {
        return new BatchForwardMessages.Builder(chatId, fromChatId, messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessages";
    }

    public static class Builder {
        private final String chatId;
        private int messageThreadId;
        private final String fromChatId;
        private boolean disableNotification;
        private boolean protectContent;
        private final long[] messageId;

        Builder(String chatId, String fromChatId, long[] messageId) {
            this.chatId = chatId;
            this.fromChatId = fromChatId;
            this.messageId = messageId;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public Builder protectContent(boolean protectContent) {
            this.protectContent = protectContent;
            return this;
        }

        public BatchForwardMessages build() {
            return new BatchForwardMessages(this.chatId, this.messageThreadId, this.fromChatId, this.disableNotification, this.protectContent, this.messageId);
        }

    }

}
