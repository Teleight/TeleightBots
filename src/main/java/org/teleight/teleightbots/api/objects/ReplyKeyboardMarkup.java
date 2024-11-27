package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ReplyKeyboardMarkup(
        @JsonProperty(value = "keyboard", required = true)
        KeyboardButton[][] keyboard,

        @JsonProperty(value = "is_persistent")
        boolean isPersistent,

        @JsonProperty(value = "resize_keyboard")
        boolean resizeKeyboard,

        @JsonProperty(value = "one_time_keyboard")
        boolean oneTimeKeyboard,

        @JsonProperty(value = "input_field_placeholder")
        @Nullable
        String inputFieldPlaceholder,

        @JsonProperty(value = "selective")
        boolean selective
) implements ReplyKeyboard {

    public Builder ofBuilder(KeyboardButton[][] keyboard) {
        return new ReplyKeyboardMarkup.Builder().keyboard(keyboard);
    }

}
