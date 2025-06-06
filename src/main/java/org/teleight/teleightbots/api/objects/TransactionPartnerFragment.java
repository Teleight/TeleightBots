package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record TransactionPartnerFragment(
        @JsonProperty(value = "withdrawal_state")
        @Nullable
        RevenueWithdrawalState withdrawalState
) implements TransactionPartner {

    @Override
    public String type() {
        return "fragment";
    }

}
