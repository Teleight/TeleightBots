package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public record WebAppInfo(
        @JsonProperty(value = "url", required = true)
        String url
) implements ApiResult {

    public static @NotNull WebAppInfo of(@NotNull String url) {
        return new WebAppInfo(url);
    }

}
