package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record UserProfileAudios(
        @JsonProperty(value = "total_count", required = true)
        int name,
        
        @JsonProperty(value = "audios", required = true)
        @NotNull
        Audio[] audios
) implements ApiResult {
}
