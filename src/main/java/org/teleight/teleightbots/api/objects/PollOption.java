package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record PollOption(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty("text_entities")
        @Nullable
        MessageEntity[] textEntities,

        @JsonProperty(value = "voter_count", required = true)
        int voterCount
) implements ApiResult {

}
