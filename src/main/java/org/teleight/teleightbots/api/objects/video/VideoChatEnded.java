package org.teleight.teleightbots.api.objects.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record VideoChatEnded(
        @JsonProperty(value = "duration", required = true)
        Integer duration
) implements ApiResult {
}
