package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReplyKeyboardRemove(
        @JsonProperty(value = "remove_keyboard", required = true)
        boolean removeKeyboard,

        @JsonProperty(value = "selective")
        boolean selective
) implements ReplyKeyboard {

}
