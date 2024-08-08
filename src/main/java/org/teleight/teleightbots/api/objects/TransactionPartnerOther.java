package org.teleight.teleightbots.api.objects;

public record TransactionPartnerOther() implements TransactionPartner {

    @Override
    public TransactionPartnerType type() {
        return TransactionPartnerType.OTHER;
    }

}
