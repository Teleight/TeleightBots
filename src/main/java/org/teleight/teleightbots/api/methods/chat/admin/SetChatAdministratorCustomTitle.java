package org.teleight.teleightbots.api.methods.chat.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record SetChatAdministratorCustomTitle(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Long userId,

        @JsonProperty(value = "custom_title", required = true)
        @NotNull
        String customTitle
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "setChatAdministratorCustomTitle";
    }

    public static class SetChatAdministratorCustomTitleBuilder {
        @Tolerate
        public SetChatAdministratorCustomTitleBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
