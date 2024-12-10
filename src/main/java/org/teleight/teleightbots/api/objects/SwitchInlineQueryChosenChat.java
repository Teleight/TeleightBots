package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record SwitchInlineQueryChosenChat(
        @JsonProperty(value = "query")
        @Nullable
        String query,

        @JsonProperty(value = "allow_user_chats")
        boolean allowUserChats,

        @JsonProperty(value = "allow_bot_chats")
        boolean allowBotChats,

        @JsonProperty(value = "allow_group_chats")
        boolean allowGroupChats,

        @JsonProperty(value = "allow_channel_chats")
        boolean allowChannelChats
) implements ApiResult {
}
