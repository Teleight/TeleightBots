package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record UserProfilePhotos(
        @JsonProperty(value = "total_count", required = true)
        @NotNull
        Integer totalCount,

        @JsonProperty(value = "photos", required = true)
        @NotNull
        PhotoSize[][] photos
) implements ApiResult {
}
