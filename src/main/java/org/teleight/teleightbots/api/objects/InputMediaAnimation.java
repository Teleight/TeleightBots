package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaAnimation(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

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

        @JsonProperty(value = "width")
        int width,

        @JsonProperty(value = "height")
        int height,

        @JsonProperty(value = "duration")
        int duration,

        @JsonProperty(value = "has_spoiler")
        boolean hasSpoiler
) implements InputMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputMediaAnimation.Builder().media(media);
    }

    @Override
    public String type() {
        return "animation";
    }

}
