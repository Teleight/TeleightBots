package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Birthdate(
        @JsonProperty(value = "day", required = true)
        @NotNull
        Integer day,

        @JsonProperty(value = "month", required = true)
        @NotNull
        Integer month,

        @JsonProperty(value = "year")
        @Nullable
        Integer year
) implements ApiResult {
}
