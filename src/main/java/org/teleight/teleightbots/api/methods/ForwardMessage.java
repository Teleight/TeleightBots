package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ForwardMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "video_start_timestamp")
        int videoStartTimestamp,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String fromChatId, int messageId) {
        return new ForwardMessage.Builder().chatId(chatId).fromChatId(fromChatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessage";
    }

}
