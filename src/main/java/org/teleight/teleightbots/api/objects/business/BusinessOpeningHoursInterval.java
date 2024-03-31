package org.teleight.teleightbots.api.objects.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessOpeningHoursInterval(
        @JsonProperty(value = "opening_minute", required = true)
        //@Range(from = 0)
        @NotNull
        Integer openingMinute,

        @JsonProperty(value = "closing_minute", required = true)
        //@Range(from = 0)
        @NotNull
        Integer closingMinute
) implements ApiResult {
}
