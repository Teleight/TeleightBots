package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record User(
        @JsonProperty("id")
        long id,

        @JsonProperty("username")
        String username
) implements ApiResult {

}
