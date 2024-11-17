package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record OrderInfo(
        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "phone_number")
        @Nullable
        String phoneNumber,

        @JsonProperty(value = "email")
        @Nullable
        String email,

        @JsonProperty(value = "shipping_address")
        @Nullable
        ShippingAddress shippingAddress
) implements ApiResult {

}
