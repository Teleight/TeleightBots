package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineKeyboardMarkup(
        @JsonProperty(value = "inline_keyboard", required = true)
        @NotNull
        InlineKeyboardButton[][] keyboard
) implements ReplyKeyboard {

    public Builder ofBuilder(InlineKeyboardButton[][] keyboard) {
        return new InlineKeyboardMarkup.Builder().keyboard(keyboard);
    }

}
