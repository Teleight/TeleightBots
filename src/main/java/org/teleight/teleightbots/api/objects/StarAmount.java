package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

public record StarAmount(
        @JsonProperty(value = "amount", required = true)
        int amount,

        @JsonProperty(value = "nanostar_amount")
        @Range(from = -999999999, to = 999999999)
        int nanostarAmount
) implements ApiResult {

}
