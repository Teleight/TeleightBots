package org.teleight.teleightbots.api.objects;

public record TransactionPartnerOther() implements TransactionPartner {

    @Override
    public String type() {
        return "other";
    }

}
