package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ForumTopicEdited(
        @JsonProperty("name")
        @Nullable
        String name,

        @JsonProperty("icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId
) implements ApiResult {
}
