package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultPhoto(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "photo_url", required = true)
        @NotNull
        String photoUrl,

        @JsonProperty(value = "thumbnail_url", required = true)
        @NotNull
        String thumbnailUrl,

        @JsonProperty(value = "photo_width")
        int photoWidth,

        @JsonProperty(value = "photo_height")
        int photoHeight,

        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "description")
        @Nullable
        String description,

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

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

        public static @NotNull Builder ofBuilder(@NotNull String id, @NotNull String photoUrl, @NotNull String thumbnailUrl) {
                return new Builder(id, photoUrl, thumbnailUrl);
        }

        @Override
        public InlineQueryResultType type() {
                return InlineQueryResultType.PHOTO;
        }

        public static final class Builder {
                private String id;
                private String photoUrl;
                private String thumbnailUrl;
                private int photoWidth;
                private int photoHeight;
                private String title;
                private String description;
                private String caption;
                private ParseMode parseMode;
                private MessageEntity[] captionEntities;
                private boolean showCaptionAboveMedia;
                private ReplyKeyboard replyMarkup;
                private InputMessageContent inputMessageContent;

                public Builder(@NotNull String id, @NotNull String photoUrl, @NotNull String thumbnailUrl) {
                        this.id = id;
                }

                public @NotNull Builder photoWidth(int photoWidth) {
                        this.photoWidth = photoWidth;
                        return this;
                }

                public @NotNull Builder photoHeight(int photoHeight) {
                        this.photoHeight = photoHeight;
                        return this;
                }

                public @NotNull Builder title(@Nullable String title) {
                        this.title = title;
                        return this;
                }

                public @NotNull Builder description(@Nullable String description) {
                        this.description = description;
                        return this;
                }

                public @NotNull Builder caption(@Nullable String caption) {
                        this.caption = caption;
                        return this;
                }

                public @NotNull Builder parseMode(@Nullable ParseMode parseMode) {
                        this.parseMode = parseMode;
                        return this;
                }

                public @NotNull Builder captionEntities(@Nullable MessageEntity[] captionEntities) {
                        this.captionEntities = captionEntities;
                        return this;
                }

                public @NotNull Builder showCaptionAboveMedia(boolean showCaptionAboveMedia) {
                        this.showCaptionAboveMedia = showCaptionAboveMedia;
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

                public @NotNull InlineQueryResultPhoto build() {
                        return new InlineQueryResultPhoto(id, photoUrl, thumbnailUrl, photoWidth, photoHeight, title, description, caption, parseMode, captionEntities, showCaptionAboveMedia, replyMarkup, inputMessageContent);
                }
        }

}
