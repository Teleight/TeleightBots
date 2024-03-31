package org.teleight.teleightbots.api.methods.chat.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record SetChatDescription(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "description", required = true)
        @NotNull
        String description
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "setChatDescription";
    }

    public static class SetChatDescriptionBuilder {
        @Tolerate
        public SetChatDescriptionBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
