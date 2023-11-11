package org.teleight.teleightbots.api.objects.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.User;

public record InlineQuery(
        @JsonProperty("id")
        String id,

        @JsonProperty("from")
        User from,

        @JsonProperty("query")
        String query,

        @JsonProperty("offset")
        String offset,

        @JsonProperty("chat_type")
        @Nullable
        String chatType,

        @JsonProperty("location")
        @Nullable
        Location location
) implements ApiResult {
}
