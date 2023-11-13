package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record File(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty("file_size")
        @Nullable
        Long fileSize,

        /// Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
        @JsonProperty("file_path")
        @Nullable
        String filePath
) implements ApiResult {
}
