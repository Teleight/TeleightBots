package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record VideoChatEnded(
        @JsonProperty(value = "duration", required = true)
        int duration
) implements ApiResult {
}
