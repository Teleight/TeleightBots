package org.teleight.teleightbots.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record PassportFile(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "file_size")
        Integer fileSize,

        @JsonProperty(value = "file_date")
        Integer fileDate
) implements ApiResult {
}
