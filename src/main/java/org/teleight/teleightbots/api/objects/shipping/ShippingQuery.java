package org.teleight.teleightbots.api.objects.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ShippingQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty(value = "invoice_payload", required = true)
        String invoicePayload,

        @JsonProperty(value = "shipping_address", required = true)
        ShippingAddress shippingAddress
) implements ApiResult {
}
