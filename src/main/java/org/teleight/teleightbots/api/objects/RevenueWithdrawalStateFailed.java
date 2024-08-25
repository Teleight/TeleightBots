package org.teleight.teleightbots.api.objects;

public record RevenueWithdrawalStateFailed() implements RevenueWithdrawalState {

    @Override
    public String type() {
        return "failed";
    }

}
