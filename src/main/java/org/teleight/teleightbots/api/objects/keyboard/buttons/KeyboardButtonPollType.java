package org.teleight.teleightbots.api.objects.keyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonPollType(
        @JsonProperty(value = "type")
        @Nullable
        String type
) implements ApiResult {

}
