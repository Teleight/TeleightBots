package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record PinChatMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification
) implements ApiMethodBoolean {

    public static Builder of(String chatId, int messageId) {
        return new PinChatMessage.Builder(chatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "pinChatMessage";
    }

    public static class Builder {
        private final String chatId;
        private final int messageId;
        private boolean disableNotification;

        Builder(String chatId, Integer messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public PinChatMessage build() {
            return new PinChatMessage(this.chatId, this.messageId, this.disableNotification);
        }
    }
}
