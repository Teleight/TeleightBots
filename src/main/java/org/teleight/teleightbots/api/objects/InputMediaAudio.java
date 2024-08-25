package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaAudio(
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

        @JsonProperty("disable_content_type_detection")
        boolean disableContentTypeDetection
) implements InputMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputMediaAudio.Builder().media(media);
    }

    @Override
    public String type() {
        return "audio";
    }

}
