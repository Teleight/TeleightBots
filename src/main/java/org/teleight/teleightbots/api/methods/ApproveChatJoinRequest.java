package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record ApproveChatJoinRequest(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, long userId) {
        return new ApproveChatJoinRequest.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "approveChatJoinRequest";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;

        Builder(String chatId, long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public ApproveChatJoinRequest build() {
            return new ApproveChatJoinRequest(this.chatId, this.userId);
        }
    }
}
