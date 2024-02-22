package org.teleight.teleightbots.api.objects.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record VideoChatScheduled(
        @JsonProperty(value = "start_date", required = true)
        Date startDate
) implements ApiResult {
}
