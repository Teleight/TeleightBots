package org.teleight.teleightbots.api.objects.origin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

import java.util.Date;

public record MessageOriginUser(
        @JsonProperty(value = "type", required = true, defaultValue = "user")
        @NotNull
        String type,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "sender_user", required = true)
        @NotNull
        User senderUser
) implements MessageOrigin {
}
