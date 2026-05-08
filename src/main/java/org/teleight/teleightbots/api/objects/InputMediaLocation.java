package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaLocation(
        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "horizontal_accuracy")
        @IntRange(from = 0, to = 1500)
        float horizontal_accuracy
) implements InputPollMedia, InputPollOptionMedia {

    public static @NotNull Builder ofBuilder(float latitude, float longitude) {
        return new InputMediaLocation.Builder().latitude(latitude).longitude(longitude);
    }

    @Override
    public String type() {
        return "location";
    }

}
