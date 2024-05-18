package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

public record ForwardMessage(
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

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodMessage {

    public static Builder of(String chatId, String fromChatId, int messageId) {
        return new ForwardMessage.Builder(chatId, fromChatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessage";
    }

    public static class Builder {
        private final String chatId;
        private int messageThreadId;
        private final String fromChatId;
        private boolean disableNotification;
        private boolean protectContent;
        private final int messageId;

        Builder(String chatId, String fromChatId, int messageId) {
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

        public ForwardMessage build() {
            return new ForwardMessage(this.chatId, this.messageThreadId, this.fromChatId, this.disableNotification, this.protectContent, this.messageId);
        }
    }

}
