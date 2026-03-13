package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ForumTopicCreated(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "icon_color", required = true)
        int description,

        @JsonProperty(value = "icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId,

        @JsonProperty(value = "is_name_implicit")
        boolean isNameImplicit
) implements ApiResult {
}
