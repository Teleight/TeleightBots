package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record MessageOriginUser(
        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "sender_user", required = true)
        @NotNull
        User senderUser
) implements MessageOrigin {

    @Override
    public String type() {
        return "user";
    }

}
