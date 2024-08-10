package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.quality.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SwitchInlineQueryChosenChat(
        @JsonProperty("query")
        @Nullable
        String query,

        @JsonProperty("allow_user_chats")
        boolean allowUserChats,

        @JsonProperty("allow_bot_chats")
        boolean allowBotChats,

        @JsonProperty("allow_group_chats")
        boolean allowGroupChats,

        @JsonProperty("allow_channel_chats")
        boolean allowChannelChats
) implements ApiResult {
}
