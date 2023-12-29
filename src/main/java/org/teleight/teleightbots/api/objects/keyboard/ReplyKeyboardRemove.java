package org.teleight.teleightbots.api.objects.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record ReplyKeyboardRemove(
        @JsonProperty(value = "remove_keyboard", required = true)
        boolean removeKeyboard,

        @JsonProperty(value = "selective")
        @Nullable
        Boolean selective
) implements ReplyKeyboard {

}
