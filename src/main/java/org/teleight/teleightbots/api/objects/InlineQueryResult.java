package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public interface InlineQueryResult extends ApiResult {

    @JsonProperty(value = "type")
    String type();

}
