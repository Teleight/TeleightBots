package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record PreCheckoutQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "currency", required = true)
        String currency,

        @JsonProperty(value = "total_amount", required = true)
        int totalAmount,

        @JsonProperty(value = "invoice_payload", required = true)
        String invoicePayload,

        @JsonProperty("shipping_option_id")
        @Nullable
        String shippingOptionId,

        @JsonProperty("order_info")
        @Nullable
        OrderInfo orderInfo
) implements ApiResult {

}
