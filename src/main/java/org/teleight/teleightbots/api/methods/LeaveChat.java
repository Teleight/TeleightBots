package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record LeaveChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements ApiMethodBoolean {

    public static Builder of(String chatId) {
        return new LeaveChat.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "leaveChat";
    }

    public static class Builder {
        private final String chatId;

        public Builder(String chatId) {
            this.chatId = chatId;
        }

        public LeaveChat build() {
            return new LeaveChat(this.chatId);
        }
    }
}
