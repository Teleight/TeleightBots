package org.teleight.teleightbots.api.objects.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.shipping.ShippingAddress;

public record OrderInfo(
        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty("phone_number")
        @Nullable
        String phoneNumber,

        @JsonProperty("email")
        @Nullable
        String email,

        @JsonProperty("shipping_address")
        @Nullable
        ShippingAddress shippingAddress
) implements ApiResult {

}
