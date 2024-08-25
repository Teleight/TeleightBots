package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record MessageId(
        @JsonProperty(value = "message_id", required = true)
        int messageId
) implements ApiResult {
}
