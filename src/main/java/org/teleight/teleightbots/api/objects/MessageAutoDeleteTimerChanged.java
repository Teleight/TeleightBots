package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record MessageAutoDeleteTimerChanged(
        @JsonProperty(value = "message_auto_delete_time", required = true)
        int messageAutoDeleteTime
) implements ApiResult {
}
