package org.teleight.teleightbots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

//chat_id 	Integer or String 	Yes 	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
//message_thread_id 	Integer 	Optional 	Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
//video_note 	InputFile or String 	Yes 	Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More information on Sending Files ». Sending video notes by a URL is currently unsupported
//duration 	Integer 	Optional 	Duration of sent video in seconds
//length 	Integer 	Optional 	Video width and height, i.e. diameter of the video message
//thumbnail 	InputFile or String 	Optional 	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More information on Sending Files »
//disable_notification 	Boolean 	Optional 	Sends the message silently. Users will receive a notification with no sound.
//protect_content 	Boolean 	Optional 	Protects the contents of the sent message from forwarding and saving
//reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
//allow_sending_without_reply 	Boolean 	Optional 	Pass True if the message should be sent even if the specified replied-to message is not found
//reply_markup 	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
@Builder
public record SendVideoNote(
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
        return "sendVideoNote";
    }

    @Override
    public void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException {
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
