package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

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
