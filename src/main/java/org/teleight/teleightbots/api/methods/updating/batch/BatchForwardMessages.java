package org.teleight.teleightbots.api.methods.updating.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;

@Builder
public record BatchForwardMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty("message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "message_ids", required = true)
        @NotNull
        Integer[] messageId
) implements ApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "forwardMessages";
    }

    public static class BatchForwardMessagesBuilder {
        @Tolerate
        public BatchForwardMessagesBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public BatchForwardMessagesBuilder fromChatId(@NotNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }

}
