package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record PollAnswer(
        @JsonProperty(value = "poll_id", required = true)
        String pollId,

        @JsonProperty(value = "chat")
        @Nullable
        Chat chat,

        @JsonProperty(value = "user")
        @Nullable
        User user,

        @JsonProperty(value = "option_ids", required = true)
        Integer[] optionIds
) implements ApiResult {

}
