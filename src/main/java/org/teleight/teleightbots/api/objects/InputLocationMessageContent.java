package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record InputLocationMessageContent(
        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "horizontal_accuracy")
        @Nullable
        Float horizontalAccuracy,

        @JsonProperty(value = "live_period")
        @Nullable
        Integer livePeriod,

        @JsonProperty(value = "heading")
        @Nullable
        Integer heading,

        @JsonProperty(value = "proximity_alert_radius")
        @Nullable
        Integer proximityAlertRadius
) implements InputMessageContent {
}
