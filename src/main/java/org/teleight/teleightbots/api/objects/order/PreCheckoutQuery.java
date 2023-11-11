package org.teleight.teleightbots.api.objects.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record PreCheckoutQuery(
        @JsonProperty("id")
        String id,

        @JsonProperty("from")
        User from,

        @JsonProperty("currency")
        String currency,

        @JsonProperty("total_amount")
        int totalAmount,

        @JsonProperty("invoice_payload")
        String invoicePayload,

        @JsonProperty("shipping_option_id")
        @Nullable
        String shippingOptionId,

        @JsonProperty("order_info")
        @Nullable
        OrderInfo orderInfo
) implements ApiResult {

}
