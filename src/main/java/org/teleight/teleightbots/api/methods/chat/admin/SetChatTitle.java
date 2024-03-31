package org.teleight.teleightbots.api.methods.chat.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record SetChatTitle(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "setChatTitle";
    }

    public static class SetChatTitleBuilder {
        @Tolerate
        public SetChatTitleBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
