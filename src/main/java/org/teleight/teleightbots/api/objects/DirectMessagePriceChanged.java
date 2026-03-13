package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record DirectMessagePriceChanged(
        @JsonProperty(value = "are_direct_messages_enabled", required = true)
        boolean areDirectMessagesEnabled,

        @JsonProperty(value = "direct_message_star_count")
        int directMessageStarCount
) implements ApiResult {
}
