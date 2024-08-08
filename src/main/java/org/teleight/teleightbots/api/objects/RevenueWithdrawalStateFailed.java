package org.teleight.teleightbots.api.objects;

public record RevenueWithdrawalStateFailed() implements RevenueWithdrawalState {

    @Override
    public RevenueWithdrawalStateType type() {
        return RevenueWithdrawalStateType.FAILED;
    }

}
