package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionPartnerTelegramApi(
        @JsonProperty(value = "request_count", required = true)
        int requestCount
) implements TransactionPartner {

    @Override
    public String type() {
        return "telegram_api";
    }

}
