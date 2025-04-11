package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record PaidMessagePriceChanged(
        @JsonProperty(value = "paid_message_star_count", required = true)
        int paidMessageStarCount
) implements ApiResult {
}
