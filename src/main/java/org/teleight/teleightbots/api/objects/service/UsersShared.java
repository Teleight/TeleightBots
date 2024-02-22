package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record UsersShared(
        @JsonProperty(value = "request_id", required = true)
        Integer requestId,

        @JsonProperty(value = "user_ids", required = true)
        Long[] userId
) implements ApiResult {
}
