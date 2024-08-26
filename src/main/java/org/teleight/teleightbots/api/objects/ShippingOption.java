package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ShippingOption(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "prices", required = true)
        @NotNull
        LabeledPrice[] prices
) implements ApiResult {
}
