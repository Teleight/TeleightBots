package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ForceReplyKeyboard(
        @JsonProperty(value = "force_reply", required = true)
        boolean forceReply,

        @JsonProperty(value = "input_field_placeholder")
        @Nullable
        String inputFieldPlaceholder,

        @JsonProperty(value = "selective")
        boolean selective
) implements ReplyKeyboard {

    public Builder ofBuilder(boolean forceReply) {
        return new ForceReplyKeyboard.Builder().forceReply(forceReply);
    }

}
