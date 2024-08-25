package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record RefundedPayment(
        @JsonProperty(value = "currency", required = true)
        @NotNull
        String currency,

        @JsonProperty(value = "total_amount", required = true)
        int totalAmount,

        @JsonProperty(value = "invoice_payload", required = true)
        @NotNull
        String invoicePayload,

        @JsonProperty(value = "telegram_payment_charge_id", required = true)
        @NotNull
        String telegramPaymentChargeId,

        @JsonProperty("provider_payment_charge_id")
        @Nullable
        String providerPaymentChargeId
) implements ApiResult {
}
