package org.teleight.teleightbots.api.objects.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record WebAppInfo(
        @JsonProperty(value = "url", required = true)
        String url
) implements ApiResult {

}
