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

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.PHOTO;
    }

}
