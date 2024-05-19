package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessOpeningHoursInterval(
        @JsonProperty(value = "opening_minute", required = true)
        @Range(from = 0, to = 7 * 24 * 60)
        int openingMinute,

        @JsonProperty(value = "closing_minute", required = true)
        @Range(from = 0, to = 8 * 24 * 60)
        int closingMinute
) implements ApiResult {
}
