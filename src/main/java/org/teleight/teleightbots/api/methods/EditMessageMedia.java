package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputMedia;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;

public record EditMessageMedia(
        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "media", required = true)
        @NotNull
        InputMedia media,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static Builder ofBuilder(InputMedia media) {
        return new EditMessageMedia.Builder(media);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageMedia";
    }

    public static class Builder {
        private String chatId;
        private int messageId;
        private String inlineMessageId;
        private final InputMedia media;
        private ReplyKeyboard replyKeyboard;

        public Builder(InputMedia media) {
            this.media = media;
        }

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder inlineMessageId(String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public Builder replyKeyboard(ReplyKeyboard replyKeyboard) {
            this.replyKeyboard = replyKeyboard;
            return this;
        }

        public EditMessageMedia build() {
            return new EditMessageMedia(this.chatId, this.messageId, this.inlineMessageId, this.media, this.replyKeyboard);
        }
    }
}
