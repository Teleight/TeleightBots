package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record BanChatMember(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "until_date")
        int untilDate,

        @JsonProperty(value = "revoke_messages")
        boolean revokeMessages
) implements ApiMethodBoolean {

    public static Builder of(String chatId, long userId) {
        return new BanChatMember.Builder(chatId, userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "banChatMember";
    }

    public static class Builder {
        private final String chatId;
        private final long userId;
        private int untilDate;
        private boolean revokeMessages;

        Builder(String chatId, long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        public Builder untilDate(int untilDate) {
            this.untilDate = untilDate;
            return this;
        }

        public Builder revokeMessages(boolean revokeMessages) {
            this.revokeMessages = revokeMessages;
            return this;
        }

        public BanChatMember build() {
            return new BanChatMember(this.chatId, this.userId, this.untilDate, this.revokeMessages);
        }
    }
}
