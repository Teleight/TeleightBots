package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputProfilePhotoAnimated(
        @JsonProperty(value = "animation", required = true)
        @NotNull
        InputFile animation,

        @JsonProperty(value = "main_frame_timestamp")
        float mainFrameTimestamp
) implements InputProfilePhoto {

    public static @NotNull Builder ofBuilder(InputFile animation) {
        return new InputProfilePhotoAnimated.Builder().animation(animation);
    }

    @Override
    public String type() {
        return "animated";
    }
}
