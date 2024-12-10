package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record PreparedInlineMessage(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "expiration_date", required = true)
        @NotNull
        Date expirationDate
) implements ApiResult {
}
