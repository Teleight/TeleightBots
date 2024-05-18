package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record ChatBoost(
        @JsonProperty(value = "boost_id", required = true)
        @NotNull
        String boostId,

        @JsonProperty(value = "add_date", required = true)
        @NotNull
        Date addDate,

        @JsonProperty(value = "expiration_date", required = true)
        @NotNull
        Date expirationDate,

        @JsonProperty(value = "source", required = true)
        @NotNull
        ChatBoostSource source
) implements ApiResult {
}
