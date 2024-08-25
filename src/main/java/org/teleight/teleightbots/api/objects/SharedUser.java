package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SharedUser(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "first_name")
        @Nullable
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "username")
        @Nullable
        String username,

        @JsonProperty(value = "photo")
        @Nullable
        PhotoSize[] photo
) implements ApiResult {
}
