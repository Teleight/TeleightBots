package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AcceptedGiftTypes(
        @JsonProperty(value = "unlimited_gifts", required = true)
        boolean unlimitedGifts,

        @JsonProperty(value = "limited_gifts", required = true)
        boolean limitedGifts,

        @JsonProperty(value = "unique_gifts", required = true)
        boolean uniqueGifts,

        @JsonProperty(value = "premium_subscription", required = true)
        boolean premiumSubscription,

        @JsonProperty(value = "gifts_from_channels", required = true)
        boolean giftsFromChannels
) implements ApiResult {

}
