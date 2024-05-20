package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record VideoNote(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "length", required = true)
        int length,

        @JsonProperty(value = "duration", required = true)
        int duration,

        @JsonProperty("thumbnail")
        @Nullable
        PhotoSize thumbnail,

        @JsonProperty("file_size")
        @Nullable
        Long fileSize
) implements ApiResult {
}
