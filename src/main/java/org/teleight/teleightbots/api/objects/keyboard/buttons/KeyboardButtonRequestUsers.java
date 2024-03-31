package org.teleight.teleightbots.api.objects.keyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonRequestUsers(
        @JsonProperty(value = "request_id", required = true)
        Integer requestId,

        @JsonProperty(value = "user_is_bot")
        @Nullable
        Boolean userIsBot,

        @JsonProperty(value = "user_is_premium")
        @Nullable
        Boolean userIsPremium,

        @JsonProperty(value = "max_quantity")
        @Nullable
        Integer maxQuantity,

        @JsonProperty(value = "request_name")
        @Nullable
        Boolean requestName,

        @JsonProperty(value = "request_username")
        @Nullable
        Boolean requestUsername,

        @JsonProperty(value = "request_photo")
        @Nullable
        Boolean requestPhoto
) implements ApiResult {
}
