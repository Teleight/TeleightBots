package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record Chat(
        @JsonProperty("id")
        int id
) implements ApiResult {
}
