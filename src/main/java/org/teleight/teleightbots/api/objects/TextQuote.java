package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;

public record TextQuote(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "position")
        @Nullable
        Integer position,

        @JsonProperty(value = "is_manual")
        @Nullable
        Boolean isManual
) implements ApiResult {
}
