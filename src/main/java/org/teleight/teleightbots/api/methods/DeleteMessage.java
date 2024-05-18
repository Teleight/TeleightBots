package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record DeleteMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, int messageId) {
        return new DeleteMessage.Builder(chatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMessage";
    }

    public static class Builder {
        private final String chatId;
        private final int messageId;

        Builder(String chatId, int messageId) {
            this.chatId = chatId;
            this.messageId = messageId;
        }

        public DeleteMessage build() {
            return new DeleteMessage(this.chatId, this.messageId);
        }
    }

}
