package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ForceReplyKeyboard(
        @JsonProperty(value = "force_reply", required = true)
        boolean forceReply,

        @JsonProperty(value = "input_field_placeholder")
        @Nullable
        String inputFieldPlaceholder,

        @JsonProperty(value = "selective")
        boolean selective
) implements ReplyKeyboard {

}
