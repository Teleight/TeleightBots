package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InputMediaAudio(
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

        @JsonProperty("disable_content_type_detection")
        boolean disableContentTypeDetection
) implements InputMedia {

    @Override
    public String type() {
        return "audio";
    }

}
