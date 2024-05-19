package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultGif(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "gif_url", required = true)
        @NotNull
        String gifUrl,

        @JsonProperty(value = "gif_width")
        int gifWidth,

        @JsonProperty(value = "gif_height")
        int gifHeight,

        @JsonProperty(value = "gif_duration")
        int gifDuration,

        @JsonProperty(value = "thumbnail_url", required = true)
        @NotNull
        String thumbnailUrl,

        @JsonProperty(value = "thumbnail_mime_type")
        @Nullable
        String thumbnailMimeType,

        @JsonProperty(value = "title")
        @Nullable
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

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.INLINE_QUERY_RESULT_GIF;
    }

}
