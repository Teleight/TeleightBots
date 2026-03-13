package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record SuggestedPostParameters(
        @JsonProperty(value = "price")
        @Nullable
        SuggestedPostPrice price,

        @JsonProperty(value = "send_date")
        int sendDate
) implements ApiResult {
}
