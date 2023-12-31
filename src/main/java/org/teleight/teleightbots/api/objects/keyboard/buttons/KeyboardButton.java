package org.teleight.teleightbots.api.objects.keyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.webapp.WebAppInfo;

public record KeyboardButton(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "request_users")
        KeyboardButtonRequestUsers requestUsers,

        @JsonProperty(value = "request_chat")
        KeyboardButtonRequestChat requestChat,

        @JsonProperty(value = "request_contact")
        Boolean requestContact,

        @JsonProperty(value = "request_location")
        Boolean requestLocation,

        @JsonProperty(value = "request_poll")
        KeyboardButtonPollType requestPoll,

        @JsonProperty(value = "web_app")
        WebAppInfo webApp
) implements ApiResult {

}
