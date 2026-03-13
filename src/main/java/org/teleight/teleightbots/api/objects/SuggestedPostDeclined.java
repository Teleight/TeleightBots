package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SuggestedPostDeclined(
        @JsonProperty(value = "suggested_post_message")
        @Nullable
        Message suggestedPostMessage,

        @JsonProperty(value = "comment")
        @Nullable
        String comment
) implements ApiResult {
}
