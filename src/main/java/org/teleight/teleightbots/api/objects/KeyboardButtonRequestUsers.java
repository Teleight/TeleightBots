package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonRequestUsers(
        @JsonProperty(value = "request_id", required = true)
        int requestId,

        @JsonProperty(value = "user_is_bot")
        boolean userIsBot,

        @JsonProperty(value = "user_is_premium")
        boolean userIsPremium,

        @JsonProperty(value = "max_quantity")
        int maxQuantity,

        @JsonProperty(value = "request_name")
        boolean requestName,

        @JsonProperty(value = "request_username")
        boolean requestUsername,

        @JsonProperty(value = "request_photo")
        boolean requestPhoto
) implements ApiResult {
}
