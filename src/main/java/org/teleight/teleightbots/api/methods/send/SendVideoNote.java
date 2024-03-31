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
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

@Builder
public record SendVideoNote(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty(value = "video_note", required = true)
        @NotNull
        InputFile videoNote,

        @JsonProperty(value = "duration")
        @Nullable
        Integer duration,

        @JsonProperty(value = "length")
        @Nullable
        Integer length,

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

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
        return "sendVideoNote";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException {
        if (businessConnectionId != null) {
            bodyCreator.addPart("business_connection_id", businessConnectionId);
        }
        bodyCreator.addPart("video_note", videoNote.file(), videoNote.fileName());
        bodyCreator.addPart("chat_id", chatId);
        if (messageThreadId != null) {
            bodyCreator.addPart("message_thread_id", messageThreadId.toString());
        }
        if (duration != null) {
            bodyCreator.addPart("duration", duration.toString());
        }
        if (length != null) {
            bodyCreator.addPart("length", length.toString());
        }
        if (thumbnail != null) {
            bodyCreator.addPart("thumbnail", thumbnail.file(), thumbnail.fileName());
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

    public static class SendVideoNoteBuilder {
        @Tolerate
        public SendVideoNoteBuilder chatId(Long chatId) {
            return chatId(String.valueOf(chatId));
        }

        @Tolerate
        public SendVideoNoteBuilder videoNote(String videoNote) {
            throw new UnsupportedOperationException("Use videoNote(InputFile) instead");
        }

        @Tolerate
        public SendVideoNoteBuilder thumbnail(String thumbnail) {
            throw new UnsupportedOperationException("Use thumbnail(InputFile) instead");
        }
    }
}
