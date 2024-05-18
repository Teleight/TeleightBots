package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record SetChatAdministratorCustomTitle(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "custom_title", required = true)
        @NotNull
        String customTitle
) implements ApiMethodBoolean {

    public static Builder of(String chatId, long userId, String customTitle) {
        return new SetChatAdministratorCustomTitle.Builder(chatId, userId, customTitle);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatAdministratorCustomTitle";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;
        private final String customTitle;

        Builder(String chatId, long userId, String customTitle) {
            this.chatId = chatId;
            this.userId = userId;
            this.customTitle = customTitle;
        }

        public SetChatAdministratorCustomTitle build() {
            return new SetChatAdministratorCustomTitle(this.chatId, this.userId, this.customTitle);
        }
    }
}
