package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record InputVenueMessageContent(
        @JsonProperty(value = "latitude", required = true)
        float latitude,

        @JsonProperty(value = "longitude", required = true)
        float longitude,

        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "address", required = true)
        String address,

        @JsonProperty(value = "foursquare_id")
        @Nullable
        String foursquareId,

        @JsonProperty(value = "foursquare_type")
        @Nullable
        String foursquareType,

        @JsonProperty(value = "google_place_id")
        @Nullable
        String googlePlaceId,

        @JsonProperty(value = "google_place_type")
        @Nullable
        String googlePlaceType
) implements InputMessageContent {
}
