package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record UnbanChatSenderChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "sender_chat_id", required = true)
        long senderChatId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, long senderChatId) {
        return new UnbanChatSenderChat.Builder().chatId(chatId).senderChatId(senderChatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "unbanChatSenderChat";
    }

}
