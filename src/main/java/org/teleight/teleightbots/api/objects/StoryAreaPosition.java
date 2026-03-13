package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

public record StoryAreaPosition(
        @JsonProperty(value = "x_percentage", required = true)
        float xPercentage,

        @JsonProperty(value = "y_percentage", required = true)
        float yPercentage,

        @JsonProperty(value = "width_percentage", required = true)
        float widthPercentage,

        @JsonProperty(value = "height_percentage", required = true)
        float heightPercentage,

        @JsonProperty(value = "rotation_angle", required = true)
        @Range(from = 0, to = 360)
        float rotationAngle,

        @JsonProperty(value = "corner_radius_percentage", required = true)
        float cornerRadiusPercentage
) implements ApiResult {
}
