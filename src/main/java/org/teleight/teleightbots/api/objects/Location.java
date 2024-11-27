package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Location(
        @JsonProperty(value = "longitude", required = true)
        Float longitude,

        @JsonProperty(value = "latitude", required = true)
        Float latitude,

        @JsonProperty(value = "horizontal_accuracy")
        @Nullable
        Float horizontalAccuracy,

        @JsonProperty(value = "live_period")
        int livePeriod,

        @JsonProperty(value = "heading")
        int heading,

        @JsonProperty(value = "proximity_alert_radius")
        int proximityAlertRadius
) implements ApiResult {
}
