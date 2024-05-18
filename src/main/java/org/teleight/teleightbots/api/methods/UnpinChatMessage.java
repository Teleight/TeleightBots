package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record UnpinChatMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id")
        int messageId
) implements ApiMethodBoolean {

    public static Builder of(String chatId) {
        return new Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unpinChatMessage";
    }

    public static class Builder {
        private final String chatId;
        private int messageId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public UnpinChatMessage build() {
            return new UnpinChatMessage(this.chatId, this.messageId);
        }
    }
}
