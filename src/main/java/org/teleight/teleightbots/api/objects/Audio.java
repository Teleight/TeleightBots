package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Audio(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "duration", required = true)
        int duration,

        @JsonProperty(value = "performer")
        @Nullable
        String performer,

        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "file_name")
        @Nullable
        String fileName,

        @JsonProperty(value = "mime_type")
        @Nullable
        String mimeType,

        @JsonProperty(value = "file_size")
        @Nullable
        Long fileSize,

        @JsonProperty(value = "thumbnail")
        @Nullable
        PhotoSize thumbnail
) implements ApiResult {
}
