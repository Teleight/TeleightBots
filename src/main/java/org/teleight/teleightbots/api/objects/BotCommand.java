package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record BotCommand(
        @JsonProperty(value = "command", required = true)
        String command,

        @JsonProperty(value = "description", required = true)
        String description
) implements ApiResult {
}
