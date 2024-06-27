package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

public record CopyMessage(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "from_chat_id", required = true)
        @NotNull
        String fromChatId,

        @JsonProperty(value = "message_id", required = true)
        long messageId,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty("show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty(value = "disable_notification")
        boolean disableNotification,

        @JsonProperty(value = "protect_content")
        boolean protectContent,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    public static Builder ofBuilder(String chatId, String fromChatId, long messageId) {
        return new CopyMessage.Builder(chatId, fromChatId, messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "copyMessage";
    }

    public static class Builder {
        private final String chatId;
        private int messageThreadId;
        private final String fromChatId;
        private final long messageId;
        private String caption;
        private ParseMode parseMode;
        private MessageEntity[] captionEntities;
        private boolean showCaptionAboveMedia;
        private boolean disableNotification;
        private boolean protectContent;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, String fromChatId, long messageId) {
            this.chatId = chatId;
            this.fromChatId = fromChatId;
            this.messageId = messageId;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder parseMode(ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        public Builder captionEntities(MessageEntity[] captionEntities) {
            this.captionEntities = captionEntities;
            return this;
        }

        public Builder showCaptionAboveMedia(boolean showCaptionAboveMedia) {
            this.showCaptionAboveMedia = showCaptionAboveMedia;
            return this;
        }

        public Builder disableNotification(boolean disableNotification) {
            this.disableNotification = disableNotification;
            return this;
        }

        public Builder protectContent(boolean protectContent) {
            this.protectContent = protectContent;
            return this;
        }

        public Builder replyParameters(ReplyParameters replyParameters) {
            this.replyParameters = replyParameters;
            return this;
        }

        public Builder replyMarkup(ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public CopyMessage build() {
            return new CopyMessage(this.chatId, this.messageThreadId, this.fromChatId, this.messageId, this.caption, this.parseMode, this.captionEntities, this.showCaptionAboveMedia, this.disableNotification, this.protectContent, this.replyParameters, this.replyMarkup);
        }
    }
}
