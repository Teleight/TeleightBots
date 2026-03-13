package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record InputStoryContentVideo(
        @JsonProperty(value = "video", required = true)
        @NotNull
        InputFile video,

        @JsonProperty(value = "duration")
        @Range(from = 0, to = 60)
        float duration,

        @JsonProperty(value = "cover_frame_timestamp")
        float coverFrameTimestamp,

        @JsonProperty(value = "is_animation")
        boolean isAnimation
) implements InputStoryContent {

    @Override
    public String type() {
        return "video";
    }
}
