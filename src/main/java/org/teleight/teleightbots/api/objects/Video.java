package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Video(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "width", required = true)
        int width,

        @JsonProperty(value = "height", required = true)
        int height,

        @JsonProperty(value = "duration", required = true)
        int duration,

        @JsonProperty("thumbnail")
        @Nullable
        PhotoSize thumbnail,

        @JsonProperty("file_name")
        @Nullable
        String fileName,

        @JsonProperty("mime_type")
        @Nullable
        String mimeType,

        @JsonProperty("file_size")
        @Nullable
        Long fileSize
) implements ApiResult {
}
