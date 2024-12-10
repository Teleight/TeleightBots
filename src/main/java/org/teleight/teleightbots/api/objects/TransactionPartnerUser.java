package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record TransactionPartnerUser(
        @JsonProperty(value = "user", required = true)
        @NotNull
        User user,

        @JsonProperty(value = "affiliate")
        @Nullable
        AffiliateInfo affiliate,

        @JsonProperty(value = "invoice_payload")
        @Nullable
        String invoicePayload,

        @JsonProperty(value = "subscription_period")
        int subscriptionPeriod,

        @JsonProperty(value = "paid_media")
        @Nullable
        PaidMedia[] paidMedia,

        @JsonProperty(value = "paid_media_payload")
        @Nullable
        String paidMediaPayload,

        @JsonProperty(value = "gift")
        @Nullable
        Gift gift
) implements TransactionPartner {

    @Override
    public String type() {
        return "user";
    }

}
