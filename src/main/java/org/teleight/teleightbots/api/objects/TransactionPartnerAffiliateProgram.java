package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record TransactionPartnerAffiliateProgram(
        @JsonProperty(value = "sponsor_user")
        @Nullable
        User sponsorUser,

        @JsonProperty(value = "commission_per_mille", required = true)
        int commissionPerMille
) implements TransactionPartner {

    @Override
    public String type() {
        return "affiliate_program";
    }

}
