package org.teleight.teleightbots.api.objects.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;

public record BusinessLocation(
        @JsonProperty(value = "address", required = true)
        @NotNull
        String address,

        @JsonProperty(value = "location")
        @Nullable
        Location location
) implements ApiResult {
}
