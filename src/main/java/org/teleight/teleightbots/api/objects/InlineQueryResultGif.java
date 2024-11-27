package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

        @JsonProperty(value = "show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String gifUrl, String thumbnailUrl) {
        return new InlineQueryResultGif.Builder().id(id).gifUrl(gifUrl).thumbnailUrl(thumbnailUrl);
    }

    @Override
    public String type() {
        return "gif";
    }

}
