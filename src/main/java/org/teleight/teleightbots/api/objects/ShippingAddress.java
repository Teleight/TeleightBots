package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ShippingAddress(
        @JsonProperty(value = "country_code", required = true)
        String countryCode,

        @JsonProperty(value = "state", required = true)
        String state,

        @JsonProperty(value = "city", required = true)
        String city,

        @JsonProperty(value = "street_line1", required = true)
        String streetLine1,

        @JsonProperty(value = "street_line2", required = true)
        String streetLine2,

        @JsonProperty(value = "post_code", required = true)
        String postCode
) implements ApiResult {
}
