package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record MessageOriginChannel(
        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "author_signature")
        @Nullable
        String senderSignature
) implements MessageOrigin {

    @Override
    public String type() {
        return "channel";
    }

}
