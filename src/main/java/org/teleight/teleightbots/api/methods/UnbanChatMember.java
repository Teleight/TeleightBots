package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record UnbanChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "only_if_banned")
        boolean onlyIfBanned
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, long userId) {
        return new UnbanChatMember.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unbanChatMember";
    }

    public static class Builder {
        private final String chatId;
        private final Long userId;
        private boolean onlyIfBanned;

        Builder(String chatId, Long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public Builder onlyIfBanned(boolean onlyIfBanned) {
            this.onlyIfBanned = onlyIfBanned;
            return this;
        }

        public UnbanChatMember build() {
            return new UnbanChatMember(this.chatId, this.userId, this.onlyIfBanned);
        }
    }
}
