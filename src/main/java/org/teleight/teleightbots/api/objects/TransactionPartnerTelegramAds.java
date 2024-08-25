package org.teleight.teleightbots.api.objects;

public record TransactionPartnerTelegramAds() implements TransactionPartner {

    @Override
    public String type() {
        return "telegram_ads";
    }

}
