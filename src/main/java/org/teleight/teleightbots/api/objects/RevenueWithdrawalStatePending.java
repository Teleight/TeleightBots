package org.teleight.teleightbots.api.objects;

public record RevenueWithdrawalStatePending() implements RevenueWithdrawalState {

    @Override
    public String type() {
        return "pending";
    }

}
