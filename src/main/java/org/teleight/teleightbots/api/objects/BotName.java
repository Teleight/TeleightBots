package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record BotName(
        @JsonProperty(value = "name", required = true)
        String name
) implements ApiResult {
}
