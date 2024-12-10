package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record SuccessfulPayment(
        @JsonProperty(value = "currency", required = true)
        String currency,

        @JsonProperty(value = "total_amount", required = true)
        int totalAmount,

        @JsonProperty(value = "invoice_payload", required = true)
        String invoicePayload,

        @JsonProperty(value = "subscription_expiration_date")
        @Nullable
        Date subscriptionExpirationDate,

        @JsonProperty(value = "is_recurring")
        boolean isRecurring,

        @JsonProperty(value = "is_first_recurring")
        boolean isFirstRecurring,

        @JsonProperty(value = "shipping_option_id")
        @Nullable
        String shippingOptionId,

        @JsonProperty(value = "order_info")
        @Nullable
        OrderInfo orderInfo,

        @JsonProperty(value = "telegram_payment_charge_id", required = true)
        String telegramPaymentChargeId,

        @JsonProperty(value = "provider_payment_charge_id", required = true)
        String providerPaymentChargeId
) implements ApiResult {
}
