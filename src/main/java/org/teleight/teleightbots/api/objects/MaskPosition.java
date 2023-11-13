package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record MaskPosition(
        @JsonProperty(value = "point", required = true)
        String point,

        @JsonProperty(value = "x_shift", required = true)
        Float xShift,

        @JsonProperty(value = "y_shift", required = true)
        Float yShift,

        @JsonProperty(value = "scale", required = true)
        Float scale
) implements ApiResult {
}
