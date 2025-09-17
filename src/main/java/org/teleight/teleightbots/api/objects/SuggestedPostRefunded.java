package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SuggestedPostRefunded(
        @JsonProperty(value = "suggested_post_message")
        @Nullable
        Message suggestedPostMessage,

        @JsonProperty(value = "reason", required = true)
        @NotNull
        SuggestedPostRefundReason reason
) implements ApiResult {
}
