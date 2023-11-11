package org.teleight.teleightbots.api.objects.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Location;
import org.teleight.teleightbots.api.objects.User;

public record ChosenInlineResult(
        @JsonProperty("result_id")
        String resultId,

        @JsonProperty("from")
        User from,

        @JsonProperty("location")
        @Nullable
        Location location,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty("query")
        String query
) implements ApiResult {
}
