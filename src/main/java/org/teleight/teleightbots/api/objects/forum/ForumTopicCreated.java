package org.teleight.teleightbots.api.objects.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ForumTopicCreated(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "icon_color", required = true)
        Integer description,

        @JsonProperty("icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId
) implements ApiResult {
}
