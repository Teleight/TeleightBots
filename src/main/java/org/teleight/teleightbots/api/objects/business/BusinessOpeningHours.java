package org.teleight.teleightbots.api.objects.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessOpeningHours(
        @JsonProperty(value = "time_zone_name", required = true)
        @NotNull
        String timeZoneName,

        @JsonProperty(value = "opening_hours", required = true)
        @NotNull
        BusinessOpeningHoursInterval[] openingHours
) implements ApiResult {
}
