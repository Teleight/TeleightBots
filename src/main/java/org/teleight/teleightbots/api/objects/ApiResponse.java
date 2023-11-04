package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ApiResponse<T>(
        @JsonProperty("ok")
        boolean ok,

        @JsonProperty("error_code")
        int errorCode,

        @JsonProperty("description")
        String errorDescription,

        @JsonProperty("parameters")
        ResponseParameters parameters,

        @JsonProperty("result")
        T result
) implements ApiResult {

}
