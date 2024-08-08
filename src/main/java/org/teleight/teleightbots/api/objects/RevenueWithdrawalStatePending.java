package org.teleight.teleightbots.api.objects;

public record RevenueWithdrawalStatePending() implements RevenueWithdrawalState {

    @Override
    public RevenueWithdrawalStateType type() {
        return RevenueWithdrawalStateType.PENDING;
    }

}
