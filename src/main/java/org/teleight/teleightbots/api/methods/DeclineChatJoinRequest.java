package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record DeclineChatJoinRequest(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, long userId) {
        return new DeclineChatJoinRequest.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "declineChatJoinRequest";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;

        Builder(String chatId, long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public DeclineChatJoinRequest build() {
            return new DeclineChatJoinRequest(this.chatId, this.userId);
        }
    }
}
