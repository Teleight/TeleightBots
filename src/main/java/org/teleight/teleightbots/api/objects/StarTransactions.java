package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record StarTransactions(
        @JsonProperty(value = "transactions", required = true)
        @NotNull
        StarTransaction[] transactions
) implements ApiResult {
}
