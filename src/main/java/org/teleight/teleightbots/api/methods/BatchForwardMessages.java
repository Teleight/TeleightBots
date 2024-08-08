package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

@Builder(builderClassName = "Builder", toBuilder = true)
@Jacksonized
public record BatchForwardMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String fromChatId, long[] messageIds) {
        return new BatchForwardMessages.Builder().chatId(chatId).fromChatId(fromChatId).messageIds(messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessages";
    }

}
