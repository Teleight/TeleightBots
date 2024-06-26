package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Location(
        @JsonProperty(value = "longitude", required = true)
        Float longitude,

        @JsonProperty(value = "latitude", required = true)
        Float latitude,

        @JsonProperty("horizontal_accuracy")
        @Nullable
        Float horizontalAccuracy,

        @JsonProperty("live_period")
        int livePeriod,

        @JsonProperty("heading")
        int heading,

        @JsonProperty("proximity_alert_radius")
        int proximityAlertRadius
) implements ApiResult {
}
