package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonRequestManagedBot(
        @JsonProperty(value = "request_id", required = true)
        int requestId,

        @JsonProperty(value = "suggested_name")
        @Nullable
        String suggestedName,

        @JsonProperty(value = "suggested_username")
        @Nullable
        String suggestedUsername
) implements ApiResult {
}
