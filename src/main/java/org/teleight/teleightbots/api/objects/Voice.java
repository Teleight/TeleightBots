package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Voice(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "duration", required = true)
        int duration,

        @JsonProperty(value = "mime_type")
        @Nullable
        String mimeType,

        @JsonProperty(value = "file_size")
        @Nullable
        Long fileSize
) implements ApiResult {
}
