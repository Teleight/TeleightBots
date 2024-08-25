package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaVideo(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

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

        @JsonProperty("supports_streaming")
        boolean supportStreaming,

        @JsonProperty("has_spoiler")
        boolean hasSpoiler
) implements InputMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputMediaVideo.Builder().media(media);
    }

    @Override
    public String type() {
        return "video";
    }

}
