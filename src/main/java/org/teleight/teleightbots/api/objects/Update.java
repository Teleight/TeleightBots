package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;

public record Update(
        @JsonProperty("update_id")
        int updateId,

        @JsonProperty("message")
        Message message,

        @JsonProperty("callback_query")
        CallbackQuery callbackQuery
) implements ApiResult {

}
