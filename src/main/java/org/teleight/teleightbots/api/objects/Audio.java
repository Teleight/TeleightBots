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

        @JsonProperty("performer")
        @Nullable
        String performer,

        @JsonProperty("title")
        @Nullable
        String title,

        @JsonProperty("file_name")
        @Nullable
        String fileName,

        @JsonProperty("mime_type")
        @Nullable
        String mimeType,

        @JsonProperty("file_size")
        @Nullable
        Long fileSize,

        @JsonProperty("thumbnail")
        @Nullable
        PhotoSize thumbnail
) implements ApiResult {
}
