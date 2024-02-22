package org.teleight.teleightbots.api.methods.updating.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;

@Builder
public record BatchCopyMessages(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @NotNull
        Integer messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "message_ids", required = true)
        @NotNull
        Long[] messageId,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "remove_caption")
        @Nullable
        Boolean removeCaption
) implements ApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "copyMessages";
    }

    public static class BatchCopyMessagesBuilder {
        @Tolerate
        public BatchCopyMessagesBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public BatchCopyMessagesBuilder fromChatId(@NotNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }
}
