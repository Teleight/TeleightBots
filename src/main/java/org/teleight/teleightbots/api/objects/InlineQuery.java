package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record InlineQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "query", required = true)
        String query,

        @JsonProperty(value = "offset", required = true)
        String offset,

        @JsonProperty(value = "chat_type")
        @Nullable
        String chatType,

        @JsonProperty(value = "location")
        @Nullable
        Location location
) implements ApiResult {
}
