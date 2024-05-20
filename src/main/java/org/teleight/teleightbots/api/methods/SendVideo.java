package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodMessage;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyParameters;

import java.util.HashMap;
import java.util.Map;

public record SendVideo(
        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "video", required = true)
        @NotNull
        InputFile video,

        @JsonProperty(value = "duration")
        int duration,

        @JsonProperty(value = "width")
        int width,

        @JsonProperty(value = "height")
        int height,

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
        boolean hasSpoiler,

        @JsonProperty(value = "supports_streaming")
        boolean supportsStreaming,

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
) implements MultiPartApiMethodMessage {

    public static Builder ofBuilder(String chatId, InputFile video) {
        return new SendVideo.Builder(chatId, video);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendDocument";
    }

    @Override
    public Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("chat_id", chatId);
        parameters.put("message_thread_id", messageThreadId);
        parameters.put("duration", duration);
        parameters.put("width", width);
        parameters.put("height", height);
        parameters.put("caption", caption);
        parameters.put("parse_mode", parseMode);
        parameters.put("caption_entities", captionEntities);
        parameters.put("has_spoiler", hasSpoiler);
        parameters.put("supports_streaming", supportsStreaming);
        parameters.put("disable_notification", disableNotification);
        parameters.put("protect_content", protectContent);
        parameters.put("reply_parameters", replyParameters);
        parameters.put("reply_markup", replyMarkup);
        return parameters;
    }

    @Override
    public Map<String, InputFile> getInputFiles() {
        final Map<String, InputFile> files = new HashMap<>();
        files.put("video", video);
        files.put("thumbnail", thumbnail);
        return files;
    }

    public static class Builder {
        private String businessConnectionId;
        private final String chatId;
        private int messageThreadId;
        private InputFile video;
        private int duration;
        private int width;
        private int height;
        private final InputFile thumbnail;
        private String caption;
        private ParseMode parseMode;
        private MessageEntity[] captionEntities;
        private boolean hasSpoiler;
        private boolean supportsStreaming;
        private boolean disableNotification;
        private boolean protectContent;
        private ReplyParameters replyParameters;
        private ReplyKeyboard replyMarkup;

        Builder(String chatId, InputFile thumbnail) {
            this.chatId = chatId;
            this.thumbnail = thumbnail;
        }

        public Builder businessConnectionId(String businessConnectionId) {
            this.businessConnectionId = businessConnectionId;
            return this;
        }

        public Builder messageThreadId(int messageThreadId) {
            this.messageThreadId = messageThreadId;
            return this;
        }

        public Builder video(InputFile video) {
            this.video = video;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
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

        public Builder hasSpoiler(boolean hasSpoiler) {
            this.hasSpoiler = hasSpoiler;
            return this;
        }

        public Builder supportsStreaming(boolean supportsStreaming) {
            this.supportsStreaming = supportsStreaming;
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

        public SendVideo build() {
            return new SendVideo(this.businessConnectionId, this.chatId, this.messageThreadId, this.video, this.duration, this.width, this.height, this.thumbnail, this.caption, this.parseMode, this.captionEntities, this.hasSpoiler, this.supportsStreaming, this.disableNotification, this.protectContent, this.replyParameters, this.replyMarkup);
        }
    }
}