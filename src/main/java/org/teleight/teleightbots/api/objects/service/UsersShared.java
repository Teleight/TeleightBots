package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record UsersShared(
        @JsonProperty(value = "request_id", required = true)
        @NotNull
        Integer requestId,

        @JsonProperty(value = "users", required = true)
        @NotNull
        SharedUser[] users
) implements ApiResult {
}
