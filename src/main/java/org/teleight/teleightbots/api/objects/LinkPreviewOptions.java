package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder
public record LinkPreviewOptions(
        @JsonProperty(value = "is_disabled")
        @Nullable
        Boolean isDisabled,

        @JsonProperty(value = "url")
        @Nullable
        String url,

        @JsonProperty(value = "prefer_small_media")
        @Nullable
        Boolean preferSmallMedia,

        @JsonProperty(value = "prefer_large_media")
        @Nullable
        Boolean preferLargeMedia,

        @JsonProperty(value = "show_above_text")
        @Nullable
        Boolean showAboveText
) implements ApiResult {
}
