package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ReplyKeyboardMarkup(
        @JsonProperty(value = "keyboard", required = true)
        KeyboardButton[][] keyboard,

        @JsonProperty("is_persistent")
        @Nullable
        Boolean isPersistent,

        @JsonProperty("resize_keyboard")
        @Nullable
        Boolean resizeKeyboard,

        @JsonProperty("one_time_keyboard")
        @Nullable
        Boolean oneTimeKeyboard,

        @JsonProperty("input_field_placeholder")
        @Nullable
        String inputFieldPlaceholder,

        @JsonProperty("selective")
        @Nullable
        Boolean selective
) implements ReplyKeyboard {

}
