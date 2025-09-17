package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record DirectMessagesTopic(
        @JsonProperty(value = "topic_id", required = true)
        long topicId,

        @JsonProperty(value = "user")
        @Nullable
        User user
) implements ApiResult {
}
