package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

public record AffiliateInfo(
        @JsonProperty(value = "affiliate_user")
        @Nullable
        User affiliateUser,

        @JsonProperty(value = "affiliate_chat")
        @Nullable
        Chat affiliateChat,

        @JsonProperty(value = "commission_per_mille", required = true)
        int commissionPerMille,

        @JsonProperty(value = "amount", required = true)
        int amount,

        @JsonProperty(value = "nanostar_amount")
        @Range(from = -999_999_999, to = 999_999_999)
        int nanostarAmount
) implements ApiResult {
}
