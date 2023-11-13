package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Venue(
        @JsonProperty(value = "location", required = true)
        Location location,

        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "address", required = true)
        String address,

        @JsonProperty("foursquare_id")
        @Nullable
        String foursquareId,

        @JsonProperty("foursquare_type")
        @Nullable
        String foursquareType,

        @JsonProperty("google_place_id")
        @Nullable
        String googlePlaceId,

        @JsonProperty("google_place_type")
        @Nullable
        String googlePlaceType
) implements ApiResult {
}
