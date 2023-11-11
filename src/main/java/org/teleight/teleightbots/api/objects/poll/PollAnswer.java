package org.teleight.teleightbots.api.objects.poll;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.User;

public record PollAnswer(
        @JsonProperty(value = "poll_id", required = true)
        String pollId,

        @JsonProperty("chat")
        @Nullable
        Chat chat,

        @JsonProperty("user")
        @Nullable
        User user,

        @JsonProperty(value = "option_ids", required = true)
        int[] optionIds
) implements ApiResult {

}
