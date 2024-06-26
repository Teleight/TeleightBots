package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record BusinessIntro(
        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "message")
        @Nullable
        String message,

        @JsonProperty(value = "sticker")
        @Nullable
        Sticker sticker
) implements ApiResult {
}
