package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultMpeg4Gif(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "mpeg4_url", required = true)
        @NotNull
        String mpeg4Url,

        @JsonProperty(value = "mpeg4_width")
        int mpeg4Width,

        @JsonProperty(value = "mpeg4_height")
        int mpeg4Height,

        @JsonProperty(value = "mpeg4_duration")
        int mpeg4Duration,

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

    public static @NotNull Builder ofBuilder(String id, String mpeg4Url) {
        return new InlineQueryResultMpeg4Gif.Builder().id(id).mpeg4Url(mpeg4Url);
    }

    @Override
    public String type() {
        return "mpeg4_gif";
    }

}
