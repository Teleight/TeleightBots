package org.teleight.teleightbots.api.objects.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record MessageEntity(
        @JsonProperty("type")
        String type,

        @JsonProperty("offset")
        int offset,

        @JsonProperty("length")
        int length,

        @JsonProperty("url")
        @Nullable
        String url,

        @JsonProperty("user")
        @Nullable
        User user,

        @JsonProperty("language")
        @Nullable
        String language,

        @JsonProperty("custom_emoji_id")
        @Nullable
        String customEmojiId
) implements ApiResult {
}
