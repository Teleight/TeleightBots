package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ForumTopic(
        @JsonProperty(value = "message_thread_id", required = true)
        int messageThreadId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "icon_color", required = true)
        int iconColor,

        @JsonProperty(value = "icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId,

        @JsonProperty(value = "is_name_implicit")
        boolean isNameImplicit
) implements ApiResult {
}
