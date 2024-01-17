package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ChatShared(
        @JsonProperty(value = "request_id", required = true)
        Integer requestId,

        @JsonProperty(value = "chat_id", required = true)
        String chatId
) implements ApiResult {
}
