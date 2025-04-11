package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record LocationAddress(
        @JsonProperty(value = "country_code", required = true)
        @NotNull
        String countryCode,

        @JsonProperty(value = "state")
        @Nullable
        String state,

        @JsonProperty(value = "city")
        @Nullable
        String city,

        @JsonProperty(value = "street")
        @Nullable
        String street
) implements ApiResult {
}
