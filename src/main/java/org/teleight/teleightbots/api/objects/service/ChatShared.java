package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.PhotoSize;

public record ChatShared(
        @JsonProperty(value = "request_id", required = true)
        @NotNull
        Integer requestId,

        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        Long chatId,

        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "username")
        @Nullable
        String username,

        @JsonProperty(value = "photo")
        @Nullable
        PhotoSize[] photo
) implements ApiResult {
}
