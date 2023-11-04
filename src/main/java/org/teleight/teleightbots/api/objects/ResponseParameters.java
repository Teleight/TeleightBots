package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ResponseParameters(
        @JsonProperty("migrate_to_chat_id")
        long migrateToChatId,

        @JsonProperty("retry_after")
        int retryAfter
) implements ApiResult {
}
