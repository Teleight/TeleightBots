package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;

public record ChatLocation(
        @JsonProperty(value = "location", required = true)
        Location location,

        @JsonProperty(value = "address", required = true)
        String address
) implements ApiResult {
}
