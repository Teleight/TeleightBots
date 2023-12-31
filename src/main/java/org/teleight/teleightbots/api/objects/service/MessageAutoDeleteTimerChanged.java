package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record MessageAutoDeleteTimerChanged(
        @JsonProperty(value = "message_auto_delete_time", required = true)
        Integer messageAutoDeleteTime
) implements ApiResult {
}
