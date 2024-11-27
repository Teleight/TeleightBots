package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record PhotoSize(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "width", required = true)
        int width,

        @JsonProperty(value = "height", required = true)
        int height,

        @JsonProperty(value = "file_size")
        long fileSize
) implements ApiResult {
}
