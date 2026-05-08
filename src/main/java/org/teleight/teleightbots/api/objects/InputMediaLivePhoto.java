package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaLivePhoto(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

        @JsonProperty(value = "photo", required = true)
        @NotNull
        InputFile photo,

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

        @JsonProperty(value = "has_spoiler")
        boolean hasSpoiler
) implements InputMedia, InputPollMedia, InputPollOptionMedia {

    public static @NotNull Builder ofBuilder(InputFile media, InputFile photo) {
        return new InputMediaLivePhoto.Builder().media(media).photo(photo);
    }

    @Override
    public String type() {
        return "live_photo";
    }

}
