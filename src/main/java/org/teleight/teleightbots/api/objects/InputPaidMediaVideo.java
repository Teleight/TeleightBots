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

        @JsonProperty(value = "thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty(value = "cover")
        @Nullable
        InputFile cover,

        @JsonProperty(value = "start_timestamp")
        int startTimestamp,

        @JsonProperty(value = "width")
        int width,

        @JsonProperty(value = "height")
        int height,

        @JsonProperty(value = "duration")
        int duration,

        @JsonProperty(value = "supports_streaming")
        boolean supportsStreaming
) implements InputPaidMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputPaidMediaVideo.Builder().media(media);
    }

    @Override
    public String type() {
        return "video";
    }

}
