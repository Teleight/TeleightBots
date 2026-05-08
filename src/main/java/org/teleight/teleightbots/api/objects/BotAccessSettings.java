package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record BotAccessSettings(
        @JsonProperty(value = "is_access_restricted", required = true)
        boolean isAccessRestricted,

        @JsonProperty(value = "added_users")
        @Nullable
        User[] addedUsers
) implements ApiResult {
}
