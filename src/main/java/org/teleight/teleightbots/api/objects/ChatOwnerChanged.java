package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record ChatOwnerChanged(
        @JsonProperty(value = "new_owner", required = true)
        @NotNull
        User newOwner
) implements ApiResult {

}
