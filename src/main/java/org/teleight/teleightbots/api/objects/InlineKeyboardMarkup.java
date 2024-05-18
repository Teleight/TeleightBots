package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record InlineKeyboardMarkup(
        @JsonProperty(value = "inline_keyboard", required = true)
        @NotNull
        InlineKeyboardButton[][] keyboard
) implements ReplyKeyboard {

    public Builder ofBuilder(InlineKeyboardButton[][] keyboard) {
        return new InlineKeyboardMarkup.Builder(keyboard);
    }

    public static final class Builder {
        private final InlineKeyboardButton[][] keyboard;

        public Builder(InlineKeyboardButton[][] keyboard) {
            this.keyboard = keyboard;
        }

        public @NotNull InlineKeyboardMarkup build() {
            return new InlineKeyboardMarkup(keyboard);
        }
    }

}
