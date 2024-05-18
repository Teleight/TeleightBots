package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record SetChatTitle(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title
) implements ApiMethodBoolean {

    public static Builder of(String chatId, String title) {
        return new SetChatTitle.Builder(chatId, title);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatTitle";
    }

    public static class Builder {
        private final String chatId;
        private final String title;

        Builder(String chatId, String title) {
            this.chatId = chatId;
            this.title = title;
        }

        public SetChatTitle build() {
            return new SetChatTitle(this.chatId, this.title);
        }
    }
}
