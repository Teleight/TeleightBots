package org.teleight.teleightbots.api.objects;

public record TransactionPartnerTelegramAds() implements TransactionPartner {

    @Override
    public TransactionPartnerType type() {
        return TransactionPartnerType.TELEGRAM_ADS;
    }

}
