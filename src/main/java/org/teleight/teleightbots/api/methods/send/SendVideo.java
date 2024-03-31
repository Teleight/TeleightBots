package org.teleight.teleightbots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.input.InputFile;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

@Builder
public record SendVideo(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "video", required = true)
        @NotNull
        InputFile video,

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

        @JsonProperty(value = "supports_streaming")
        @Nullable
        Boolean supportsStreaming,

        @JsonProperty(value = "disable_notification")
        @Nullable
        Boolean disableNotification,

        @JsonProperty(value = "protect_content")
        @Nullable
        Boolean protectContent,

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "sendDocument";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException {
        if (businessConnectionId != null) {
            bodyCreator.addPart("business_connection_id", businessConnectionId);
        }
        bodyCreator.addPart("video", video.file(), video.fileName());
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
        if (supportsStreaming != null) {
            bodyCreator.addPart("supports_streaming", supportsStreaming.toString());
        }
        if (disableNotification != null) {
            bodyCreator.addPart("disable_notification", disableNotification.toString());
        }
        if (protectContent != null) {
            bodyCreator.addPart("protect_content", protectContent.toString());
        }
        if (replyParameters != null) {
            bodyCreator.addPart("reply_parameters", OBJECT_MAPPER.writeValueAsString(replyParameters));
        }
        if (replyMarkup != null) {
            bodyCreator.addPart("reply_markup", OBJECT_MAPPER.writeValueAsString(replyMarkup));
        }
    }

    public static class SendVideoBuilder {
        @Tolerate
        public SendVideoBuilder chatId(@NotNull Long chatId) {
            return this.chatId(chatId.toString());
        }

        @Tolerate
        public SendVideoBuilder video(@NotNull String video) {
            throw new UnsupportedOperationException("Use InputFile instead");
        }

        @Tolerate
        public SendVideoBuilder thumbnail(@NotNull String thumbnail) {
            throw new UnsupportedOperationException("Use InputFile instead");
        }
    }
}
