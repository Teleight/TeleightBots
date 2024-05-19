package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultAudio(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "audio_url", required = true)
        @NotNull
        String audioUrl,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "performer")
        @Nullable
        String performer,

        @JsonProperty(value = "audio_duration")
        @Nullable
        Integer audioDuration,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String audioUrl, String title) {
        return new InlineQueryResultAudio.Builder(id, audioUrl, title);
    }

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.INLINE_QUERY_RESULT_AUDIO;
    }

    public static final class Builder {
        private final String id;
        private final String audioUrl;
        private final String title;
        private String caption;
        private ParseMode parseMode;
        private MessageEntity[] captionEntities;
        private String performer;
        private Integer audioDuration;
        private ReplyKeyboard replyMarkup;
        private InputMessageContent inputMessageContent;

        public Builder(String id, String audioUrl, String title) {
            this.id = id;
            this.audioUrl = audioUrl;
            this.title = title;
        }

        public @NotNull Builder caption(@Nullable String caption) {
            this.caption = caption;
            return this;
        }

        public @NotNull Builder parseMode(@Nullable ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        public @NotNull Builder captionEntities(@Nullable MessageEntity... captionEntities) {
            this.captionEntities = captionEntities;
            return this;
        }

        public @NotNull Builder performer(@Nullable String performer) {
            this.performer = performer;
            return this;
        }

        public @NotNull Builder audioDuration(@Nullable Integer audioDuration) {
            this.audioDuration = audioDuration;
            return this;
        }

        public @NotNull Builder replyMarkup(@Nullable ReplyKeyboard replyMarkup) {
            this.replyMarkup = replyMarkup;
            return this;
        }

        public @NotNull Builder inputMessageContent(@Nullable InputMessageContent inputMessageContent) {
            this.inputMessageContent = inputMessageContent;
            return this;
        }

        public @NotNull InlineQueryResultAudio build() {
            return new InlineQueryResultAudio(
                    this.id,
                    this.audioUrl,
                    this.title,
                    this.caption,
                    this.parseMode,
                    this.captionEntities,
                    this.performer,
                    this.audioDuration,
                    this.replyMarkup,
                    this.inputMessageContent
            );
        }
    }
}
