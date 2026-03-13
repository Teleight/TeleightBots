package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ChatOwnerLeft(
        @JsonProperty(value = "new_owner")
        @Nullable
        User newOwner
) implements ApiResult {

}
