package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodMessage;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record BatchCopyMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "message_ids", required = true)
        long[] messageIds,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "remove_caption")
        boolean removeCaption
) implements ApiMethodMessage {

    public static @NotNull Builder ofBuilder(String chatId, String fromChatId, long[] messageIds) {
        return new BatchCopyMessages.Builder().chatId(chatId).fromChatId(fromChatId).messageIds(messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "copyMessages";
    }

}
