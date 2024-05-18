package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ChatBoostAdded(
        @JsonProperty(value = "boost_count", required = true)
        @NotNull
        Integer boostCount
) implements ApiResult {
}
