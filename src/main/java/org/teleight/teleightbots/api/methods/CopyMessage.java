package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;

@Builder
public record CopyMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @NotNull
        Integer messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Long messageId,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        String parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "reply_to_message_id")
        @Nullable
        Integer replyToMessageId,

        @JsonProperty(value = "allow_sending_without_reply")
        @Nullable
        Boolean allowSendingWithoutReply,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "copyMessage";
    }

    public static class CopyMessageBuilder {
        @Tolerate
        public CopyMessageBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public CopyMessageBuilder fromChatId(@NotNull Long chatId) {
            this.fromChatId = chatId.toString();
            return this;
        }
    }
}
