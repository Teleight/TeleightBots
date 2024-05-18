package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record ChatPhoto(
        @JsonProperty(value = "small_file_id", required = true)
        String smallFileId,

        @JsonProperty(value = "small_file_unique_id", required = true)
        String smallFileUniqueId,

        @JsonProperty(value = "big_file_id", required = true)
        String bigFileId,

        @JsonProperty(value = "big_file_unique_id", required = true)
        String bigFileUniqueId
) implements ApiResult {
}
