package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record SuggestedPostInfo(
        @JsonProperty(value = "state", required = true)
        @NotNull
        SuggestedPostState state,

        @JsonProperty(value = "price")
        @Nullable
        SuggestedPostPrice price,

        @JsonProperty(value = "send_date")
        @Nullable
        Date sendDate
) implements ApiResult {
}
