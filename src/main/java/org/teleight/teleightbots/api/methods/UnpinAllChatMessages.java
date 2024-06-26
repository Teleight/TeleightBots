package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record UnpinAllChatMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId) {
        return new UnpinAllChatMessages.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unpinAllChatMessages";
    }

    public static class Builder {
        private final String chatId;

        Builder(String chatId) {
            this.chatId = chatId;
        }

        public UnpinAllChatMessages build() {
            return new UnpinAllChatMessages(this.chatId);
        }
    }
}
