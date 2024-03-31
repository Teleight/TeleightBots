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
public record SendPhoto(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo,

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

        @JsonProperty(value = "reply_parameters")
        @Nullable
        ReplyParameters replyParameters,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MultiPartApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "sendPhoto";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) {
        if (businessConnectionId != null) {
            bodyCreator.addPart("business_connection_id", businessConnectionId);
        }
        bodyCreator.addPart("photo", photo.file(), photo.fileName());
        bodyCreator.addPart("chat_id", chatId);

        if (caption != null) {
            bodyCreator.addPart("caption", caption);
            if (parseMode != null) {
                bodyCreator.addPart("parse_mode", parseMode.getFieldValue());
            }
        }
        if (captionEntities != null) {
            try {
                bodyCreator.addPart("caption_entities", OBJECT_MAPPER.writeValueAsString(captionEntities));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
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
        if (replyParameters != null) {
            try {
                bodyCreator.addPart("reply_parameters", OBJECT_MAPPER.writeValueAsString(replyParameters));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (replyMarkup != null) {
            try {
                bodyCreator.addPart("reply_markup", OBJECT_MAPPER.writeValueAsString(replyMarkup));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class SendPhotoBuilder {
        @Tolerate
        public SendPhotoBuilder chatId(@NotNull Long chatId) {
            return this.chatId(chatId.toString());
        }

        @Tolerate
        public SendPhotoBuilder photo(@NotNull String photo) {
            throw new UnsupportedOperationException("Use photo(InputFile) instead");
        }
    }

}
