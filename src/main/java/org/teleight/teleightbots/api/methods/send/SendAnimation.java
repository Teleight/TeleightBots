package org.teleight.teleightbots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.input.InputFile;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

@Builder
public record SendAnimation(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "animation", required = true)
        @NotNull
        InputFile animation,

        @JsonProperty(value = "duration")
        @Nullable
        Integer duration,

        @JsonProperty(value = "width")
        @Nullable
        Integer width,

        @JsonProperty(value = "height")
        @Nullable
        Integer height,

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "has_spoiler")
        @Nullable
        Boolean hasSpoiler,

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
) implements MultiPartApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "sendAnimation";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException {
        bodyCreator.addPart("animation", animation.file(), animation.fileName());
        bodyCreator.addPart("chat_id", chatId);
        if (messageThreadId != null) {
            bodyCreator.addPart("message_thread_id", messageThreadId.toString());
        }
        if (duration != null) {
            bodyCreator.addPart("duration", duration.toString());
        }
        if (width != null) {
            bodyCreator.addPart("width", width.toString());
        }
        if (height != null) {
            bodyCreator.addPart("height", height.toString());
        }
        if (thumbnail != null) {
            bodyCreator.addPart("thumbnail", thumbnail.file(), thumbnail.fileName());
        }
        if (caption != null) {
            bodyCreator.addPart("caption", caption);
            if (parseMode != null) {
                bodyCreator.addPart("parse_mode", parseMode.getFieldValue());
            }
        }
        if (captionEntities != null) {
            bodyCreator.addPart("caption_entities", OBJECT_MAPPER.writeValueAsString(captionEntities));
        }
        if (hasSpoiler != null) {
            bodyCreator.addPart("has_spoiler", hasSpoiler.toString());
        }
        if (disableNotification != null) {
            bodyCreator.addPart("disable_notification", disableNotification.toString());
        }
        if (protectContent != null) {
            bodyCreator.addPart("protect_content", protectContent.toString());
        }
        if (replyToMessageId != null) {
            bodyCreator.addPart("reply_to_message_id", replyToMessageId.toString());
        }
        if (allowSendingWithoutReply != null) {
            bodyCreator.addPart("allow_sending_without_reply", allowSendingWithoutReply.toString());
        }
        if (replyMarkup != null) {
            bodyCreator.addPart("reply_markup", OBJECT_MAPPER.writeValueAsString(replyMarkup));
        }
    }

    public static class SendAnimationBuilder {
        @Tolerate
        public SendAnimationBuilder chatId(Long chatId) {
            return this.chatId(chatId.toString());
        }

        @Tolerate
        public SendAnimationBuilder animation(String animation) {
            throw new UnsupportedOperationException("Use animation(InputFile) instead");
        }

        @Tolerate
        public SendAnimationBuilder thumbnail(String thumbnail) {
            throw new UnsupportedOperationException("Use thumbnail(InputFile) instead");
        }
    }

}
