package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Game(
        @JsonProperty(value = "title", required = true)
        String title,

        @JsonProperty(value = "description", required = true)
        String description,

        @JsonProperty(value = "photo", required = true)
        PhotoSize[] photo,

        @JsonProperty(value = "text")
        @Nullable
        String text,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities,

        @JsonProperty(value = "animation")
        @Nullable
        Animation animation
) implements ApiResult {
}
