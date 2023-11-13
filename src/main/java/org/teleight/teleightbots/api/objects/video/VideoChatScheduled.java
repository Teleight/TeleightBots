package org.teleight.teleightbots.api.objects.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record VideoChatScheduled(
        @JsonProperty(value = "start_date", required = true)
        Integer startDate
) implements ApiResult {
}
