package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record BanChatSenderChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sender_chat_id", required = true)
        long senderChatId
) implements ApiMethodBoolean {

    public static Builder ofBuilder(String chatId, long senderChatId) {
        return new BanChatSenderChat.Builder(chatId, senderChatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "banChatSenderChat";
    }

    public static class Builder {
        private final String chatId;
        private final long senderChatId;

        Builder(String chatId, long senderChatId) {
            this.chatId = chatId;
            this.senderChatId = senderChatId;
        }

        public BanChatSenderChat build() {
            return new BanChatSenderChat(this.chatId, this.senderChatId);
        }
    }
}
