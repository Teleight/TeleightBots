package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ReplyKeyboardRemove(
        @JsonProperty(value = "remove_keyboard", required = true)
        boolean removeKeyboard,

        @JsonProperty(value = "selective")
        boolean selective
) implements ReplyKeyboard {

    public Builder ofBuilder(boolean removeKeyboard) {
        return new ReplyKeyboardRemove.Builder().removeKeyboard(removeKeyboard);
    }

}
