package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

public record BatchCopyMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "remove_caption")
        boolean removeCaption
) implements ApiMethodMessage {

    public static Builder ofBuilder(String chatId, String fromChatId, long[] messageIds) {
        return new BatchCopyMessages.Builder(chatId, fromChatId, messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "copyMessages";
    }

    public static class Builder {
        private final String chatId;
        private int messageThreadId;
        private final String fromChatId;
        private final long[] messageId;
        private boolean disableNotification;
        private boolean protectContent;
        private boolean removeCaption;

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

        public Builder removeCaption(boolean removeCaption) {
            this.removeCaption = removeCaption;
            return this;
        }

        public BatchCopyMessages build() {
            return new BatchCopyMessages(this.chatId, this.messageThreadId, this.fromChatId, this.messageId, this.disableNotification, this.protectContent, this.removeCaption);
        }
    }
}
