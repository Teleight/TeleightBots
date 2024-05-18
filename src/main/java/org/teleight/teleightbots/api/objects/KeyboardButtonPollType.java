package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonPollType(
        @JsonProperty(value = "type")
        @Nullable
        String type
) implements ApiResult {

}
