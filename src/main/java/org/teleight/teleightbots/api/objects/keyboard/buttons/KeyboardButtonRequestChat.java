package org.teleight.teleightbots.api.objects.keyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.admin.ChatAdministratorRights;

public record KeyboardButtonRequestChat(
        @JsonProperty(value = "request_id", required = true)
        Integer requestId,

        @JsonProperty(value = "chat_is_channel")
        @Nullable
        Boolean chatIsChannel,

        @JsonProperty(value = "chat_is_forum")
        @Nullable
        Boolean chatIsForum,

        @JsonProperty(value = "chat_has_username")
        @Nullable
        Boolean chatHasUsername,

        @JsonProperty(value = "chat_is_created")
        Boolean chatIsCreated,

        @JsonProperty(value = "user_administrator_rights")
        @Nullable
        ChatAdministratorRights userAdministratorRights,

        @JsonProperty(value = "bot_administrator_rights")
        @Nullable
        ChatAdministratorRights botAdministratorRights,

        @JsonProperty(value = "bot_is_member")
        @Nullable
        Boolean botIsMember,

        @JsonProperty(value = "request_title")
        @Nullable
        Boolean requestTitle,

        @JsonProperty(value = "request_username")
        @Nullable
        Boolean requestUsername,

        @JsonProperty(value = "request_photo")
        @Nullable
        Boolean requestPhoto
) implements ApiResult {
}
