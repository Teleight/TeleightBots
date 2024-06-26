package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record Invoice(
        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "description", required = true)
        String description,

        @JsonProperty(value = "start_parameter", required = true)
        String startParameter,

        @JsonProperty(value = "currency", required = true)
        String currency,

        @JsonProperty(value = "total_amount", required = true)
        int totalAmount
) implements ApiResult {
}
