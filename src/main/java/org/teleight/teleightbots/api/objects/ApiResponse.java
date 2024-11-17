package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ApiResponse<T>(
        @JsonProperty(value = "ok")
        boolean ok,

        @JsonProperty(value = "error_code")
        int errorCode,

        @JsonProperty(value = "description")
        String errorDescription,

        @JsonProperty(value = "parameters")
        ResponseParameters parameters,

        @JsonProperty(value = "result")
        T result
) implements ApiResult {

}
