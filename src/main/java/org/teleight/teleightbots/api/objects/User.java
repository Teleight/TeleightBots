package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record User(
        @JsonProperty(value = "id", required = true)
        Long id,

        @JsonProperty(value = "is_bot", required = true)
        Boolean isBot,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty("last_name")
        @Nullable
        String lastName,

        @JsonProperty("username")
        @Nullable
        String username,

        @JsonProperty("language_code")
        @Nullable
        String language_code,

        @JsonProperty("is_premium")
        @Nullable
        Boolean isPremium,

        @JsonProperty("added_to_attachment_menu")
        @Nullable
        Boolean addedToAttachmentMenu,

        @JsonProperty("can_join_groups")
        @Nullable
        Boolean canJoinGroups,

        @JsonProperty("can_read_all_group_messages")
        @Nullable
        Boolean canReadAllGroupMessages,

        @JsonProperty("supports_inline_queries")
        @Nullable
        Boolean supportsInlineQueries,

        @JsonProperty("can_connect_to_business")
        @Nullable
        Boolean canConnectToBusiness
) implements ApiResult {

}
