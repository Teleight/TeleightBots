package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record KeyboardButton(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "icon_custom_emoji_id")
        @Nullable
        String iconCustomEmojiId,

        @JsonProperty(value = "style")
        @Nullable
        KeyboardButtonStyle style,

        @JsonProperty(value = "request_users")
        KeyboardButtonRequestUsers requestUsers,

        @JsonProperty(value = "request_chat")
        KeyboardButtonRequestChat requestChat,

        @JsonProperty(value = "request_contact")
        boolean requestContact,

        @JsonProperty(value = "request_location")
        boolean requestLocation,

        @JsonProperty(value = "request_poll")
        KeyboardButtonPollType requestPoll,

        @JsonProperty(value = "web_app")
        WebAppInfo webApp
) implements ApiResult {

}
