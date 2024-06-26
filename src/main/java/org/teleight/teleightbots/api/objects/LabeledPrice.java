package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LabeledPrice(
        @JsonProperty(value = "label", required = true)
        String label,

        @JsonProperty(value = "amount", required = true)
        int amount
) {
}
