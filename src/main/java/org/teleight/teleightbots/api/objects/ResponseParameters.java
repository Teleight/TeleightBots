package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ResponseParameters(
        @JsonProperty(value = "migrate_to_chat_id")
        long migrateToChatId,

        @JsonProperty(value = "retry_after")
        int retryAfter
) implements ApiResult {
}
