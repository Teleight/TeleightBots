package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record GiveawayCreated(
        @JsonProperty("prize_star_count")
        int prizeStarCount
) implements ApiResult {
}
