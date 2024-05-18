package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record DeleteChatPhoto(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder of(String chatId) {
        return new DeleteChatPhoto.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteChatPhoto";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public DeleteChatPhoto build() {
            return new DeleteChatPhoto(this.chatId);
        }
    }
}
