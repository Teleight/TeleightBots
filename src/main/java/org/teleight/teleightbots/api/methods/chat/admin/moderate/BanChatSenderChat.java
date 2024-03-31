package org.teleight.teleightbots.api.methods.chat.admin.moderate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder
public record BanChatSenderChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sender_chat_id", required = true)
        @NotNull
        Long senderChatId
) implements ApiMethodBoolean {
    @Override
    public @NotNull String getEndpointURL() {
        return "banChatSenderChat";
    }

    public static class BanChatSenderChatBuilder {
        @Tolerate
        public BanChatSenderChatBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
