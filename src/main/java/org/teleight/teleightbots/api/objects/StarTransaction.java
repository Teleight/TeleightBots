package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record StarTransaction(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "amount", required = true)
        int amount,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty("source")
        @Nullable
        TransactionPartner source,

        @JsonProperty("receiver")
        @Nullable
        TransactionPartner receiver
) implements ApiResult {
}
