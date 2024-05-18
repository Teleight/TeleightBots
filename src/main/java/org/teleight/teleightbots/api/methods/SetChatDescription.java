package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record SetChatDescription(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "description", required = true)
        @NotNull
        String description
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, String description) {
        return new SetChatDescription.Builder(chatId, description);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatDescription";
    }

    public static class Builder {
        private final String chatId;
        private final String description;

        Builder(String chatId, String description) {
            this.chatId = chatId;
            this.description = description;
        }

        public SetChatDescription build() {
            return new SetChatDescription(this.chatId, this.description);
        }
    }
}
