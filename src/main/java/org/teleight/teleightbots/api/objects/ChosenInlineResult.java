package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.User;

public record ChosenInlineResult(
        @JsonProperty(value = "result_id", required = true)
        String resultId,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty("location")
        @Nullable
        Location location,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "query", required = true)
        String query
) implements ApiResult {
}
