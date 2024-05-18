package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record UnbanChatSenderChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sender_chat_id", required = true)
        long senderChatId
) implements ApiMethodBoolean {

    public static Builder of(String chatId, long senderChatId) {
        return new UnbanChatSenderChat.Builder(chatId, senderChatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unbanChatSenderChat";
    }

    public static class Builder {
        private final String chatId;
        private final Long senderChatId;

        Builder(String chatId, Long senderChatId) {
            this.chatId = chatId;
            this.senderChatId = senderChatId;
        }

        public UnbanChatSenderChat build() {
            return new UnbanChatSenderChat(this.chatId, this.senderChatId);
        }
    }
}
