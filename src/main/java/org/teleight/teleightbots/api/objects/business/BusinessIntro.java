package org.teleight.teleightbots.api.objects.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Sticker;

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
