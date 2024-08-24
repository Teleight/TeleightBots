package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputPaidMediaVideo(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

        @JsonProperty("thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty("width")
        int width,

        @JsonProperty("height")
        int height,

        @JsonProperty("duration")
        int duration,

        @JsonProperty("supports_streaming")
        boolean supportsStreaming
) implements InputPaidMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputPaidMediaVideo.Builder().media(media);
    }

    @Override
    public InputPaidMediaType type() {
        return InputPaidMediaType.VIDEO;
    }

}
