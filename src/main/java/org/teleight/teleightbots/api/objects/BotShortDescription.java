package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record BotShortDescription(
        @JsonProperty(value = "short_description", required = true)
        String shortDescription
) implements ApiResult {
}
