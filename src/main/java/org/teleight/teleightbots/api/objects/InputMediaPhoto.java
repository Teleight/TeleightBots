package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InputMediaPhoto(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

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

        @JsonProperty(value = "has_spoiler")
        boolean hasSpoiler
) implements InputMedia {

    @Override
    public InputMediaType type() {
        return InputMediaType.PHOTO;
    }

}
