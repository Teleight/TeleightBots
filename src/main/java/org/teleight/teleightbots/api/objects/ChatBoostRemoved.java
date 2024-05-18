package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record ChatBoostRemoved(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "boost_id", required = true)
        @NotNull
        String boostId,

        @JsonProperty(value = "remove_date", required = true)
        @NotNull
        Date removeDate,

        @JsonProperty(value = "source", required = true)
        @NotNull
        ChatBoostSource source
) implements ApiResult {
}
