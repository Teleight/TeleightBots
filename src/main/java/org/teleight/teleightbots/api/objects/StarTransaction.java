package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record StarTransaction(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "amount", required = true)
        int amount,

        @JsonProperty(value = "nanostar_mount")
        @Range(from = 0, to = 999_999_999)
        int nanostarAmount,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "source")
        @Nullable
        TransactionPartner source,

        @JsonProperty(value = "receiver")
        @Nullable
        TransactionPartner receiver
) implements ApiResult {
}
