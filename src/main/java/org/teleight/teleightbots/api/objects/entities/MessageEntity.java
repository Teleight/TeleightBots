package org.teleight.teleightbots.api.objects.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record MessageEntity(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "offset", required = true)
        Integer offset,

        @JsonProperty(value = "length", required = true)
        Integer length,

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
