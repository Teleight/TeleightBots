package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record LinkPreviewOptions(
        @JsonProperty(value = "is_disabled")
        boolean isDisabled,

        @JsonProperty(value = "url")
        @Nullable
        String url,

        @JsonProperty(value = "prefer_small_media")
        boolean preferSmallMedia,

        @JsonProperty(value = "prefer_large_media")
        boolean preferLargeMedia,

        @JsonProperty(value = "show_above_text")
        boolean showAboveText
) implements ApiResult {

}
