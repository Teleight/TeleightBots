package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButtonRequestChat(
        @JsonProperty(value = "request_id", required = true)
        int requestId,

        @JsonProperty(value = "chat_is_channel")
        boolean chatIsChannel,

        @JsonProperty(value = "chat_is_forum")
        boolean chatIsForum,

        @JsonProperty(value = "chat_has_username")
        boolean chatHasUsername,

        @JsonProperty(value = "chat_is_created")
        boolean chatIsCreated,

        @JsonProperty(value = "user_administrator_rights")
        @Nullable
        ChatAdministratorRights userAdministratorRights,

        @JsonProperty(value = "bot_administrator_rights")
        @Nullable
        ChatAdministratorRights botAdministratorRights,

        @JsonProperty(value = "bot_is_member")
        boolean botIsMember,

        @JsonProperty(value = "request_title")
        boolean requestTitle,

        @JsonProperty(value = "request_username")
        boolean requestUsername,

        @JsonProperty(value = "request_photo")
        boolean requestPhoto
) implements ApiResult {
}
