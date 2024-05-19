package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record Birthdate(
        @JsonProperty(value = "day", required = true)
        int day,

        @JsonProperty(value = "month", required = true)
        int month,

        @JsonProperty(value = "year")
        int year
) implements ApiResult {
}
