package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record MessageOriginHiddenUser(
        @JsonProperty(value = "type", required = true, defaultValue = "hidden_user")
        @NotNull
        String type,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "sender_user_name", required = true)
        @NotNull
        String senderUserName
) implements MessageOrigin {
}
