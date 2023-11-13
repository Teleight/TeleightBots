package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record WebAppData(
        @JsonProperty(value = "data", required = true)
        String data,

        @JsonProperty(value = "button_text", required = true)
        String buttonText
) implements ApiResult {
}
