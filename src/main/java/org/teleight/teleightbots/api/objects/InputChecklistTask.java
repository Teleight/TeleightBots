package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record InputChecklistTask(
        @JsonProperty(value = "id", required = true)
        int id,

        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities
) implements ApiResult {
}
