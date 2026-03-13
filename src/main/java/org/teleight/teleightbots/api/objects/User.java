package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record User(
        @JsonProperty(value = "id", required = true)
        Long id,

        @JsonProperty(value = "is_bot", required = true)
        boolean isBot,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "username")
        @Nullable
        String username,

        @JsonProperty(value = "language_code")
        @Nullable
        String language_code,

        @JsonProperty(value = "is_premium")
        boolean isPremium,

        @JsonProperty(value = "added_to_attachment_menu")
        boolean addedToAttachmentMenu,

        @JsonProperty(value = "can_join_groups")
        boolean canJoinGroups,

        @JsonProperty(value = "can_read_all_group_messages")
        boolean canReadAllGroupMessages,

        @JsonProperty(value = "supports_inline_queries")
        boolean supportsInlineQueries,

        @JsonProperty(value = "can_connect_to_business")
        boolean canConnectToBusiness,

        @JsonProperty(value = "has_main_web_app")
        boolean hasMainWebApp,

        @JsonProperty(value = "has_topics_enabled")
        boolean hasTopicsEnabled,

        @JsonProperty(value = "allows_users_to_create_topics")
        boolean allowsUsersToCreateTopics
) implements ApiResult {

}
