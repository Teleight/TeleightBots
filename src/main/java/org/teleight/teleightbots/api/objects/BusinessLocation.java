package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessLocation(
        @JsonProperty(value = "address", required = true)
        @NotNull
        String address,

        @JsonProperty(value = "location")
        @Nullable
        Location location
) implements ApiResult {
}
