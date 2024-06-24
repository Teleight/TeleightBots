package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record Dice(
        @JsonProperty(value = "emoji", required = true)
        String emoji,

        @JsonProperty(value = "value", required = true)
        int value
) implements ApiResult {

}
