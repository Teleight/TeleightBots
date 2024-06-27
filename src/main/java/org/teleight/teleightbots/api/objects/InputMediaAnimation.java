package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InputMediaAnimation(
        @JsonProperty(value = "media", required = true)
        @NotNull
        String media,

        @JsonProperty("thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty("caption")
        @Nullable
        String caption,

        @JsonProperty("parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty("caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty("show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty("width")
        int width,

        @JsonProperty("height")
        int height,

        @JsonProperty("duration")
        int duration,

        @JsonProperty("has_spoiler")
        boolean hasSpoiler
) implements InputMedia {

    @Override
    public InputMediaType type() {
        return InputMediaType.ANIMATION;
    }

}
