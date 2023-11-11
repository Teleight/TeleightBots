package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;

public record ChatLocation(
        @JsonProperty("location")
        Location location,

        @JsonProperty("address")
        String address
) implements ApiResult {
}
