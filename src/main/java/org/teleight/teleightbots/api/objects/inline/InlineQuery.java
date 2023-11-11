package org.teleight.teleightbots.api.objects.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.User;

public record InlineQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "query", required = true)
        String query,

        @JsonProperty(value = "offset", required = true)
        String offset,

        @JsonProperty("chat_type")
        @Nullable
        String chatType,

        @JsonProperty("location")
        @Nullable
        Location location
) implements ApiResult {
}
