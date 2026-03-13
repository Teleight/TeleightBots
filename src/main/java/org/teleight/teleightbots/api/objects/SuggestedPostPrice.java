package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record SuggestedPostPrice(
        @JsonProperty(value = "currency", required = true)
        @NotNull
        TelegramCurrency currency,

        @JsonProperty(value = "amount", required = true)
        int amount
) implements ApiResult {

}
