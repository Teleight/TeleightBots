package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ReplyKeyboardMarkup(
        @JsonProperty(value = "keyboard", required = true)
        KeyboardButton[][] keyboard,

        @JsonProperty("is_persistent")
        boolean isPersistent,

        @JsonProperty("resize_keyboard")
        boolean resizeKeyboard,

        @JsonProperty("one_time_keyboard")
        boolean oneTimeKeyboard,

        @JsonProperty("input_field_placeholder")
        @Nullable
        String inputFieldPlaceholder,

        @JsonProperty("selective")
        boolean selective
) implements ReplyKeyboard {

}
