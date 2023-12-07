package org.teleight.teleightbots.api.objects.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record InlineKeyboardMarkup(
        @JsonProperty(value = "inline_keyboard",required = true)
        InlineKeyboardButton[][] keyboard
) implements ReplyKeyboard {

    public static @NotNull InlineKeyboardMarkup of() {
        return new InlineKeyboardMarkup(null);
    }

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public @NotNull InlineKeyboardMarkup withKeyboard(@NotNull InlineKeyboardButton[][] keyboard) {
        return new InlineKeyboardMarkup(keyboard);
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder keyboard(@NotNull InlineKeyboardButton[][] keyboard);

        @NotNull InlineKeyboardMarkup build();
    }

    static final class BuilderImpl implements Builder {
        private InlineKeyboardButton[][] keyboard;

        public @NotNull Builder keyboard(@NotNull InlineKeyboardButton[][] keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public @NotNull InlineKeyboardMarkup build() {
            return new InlineKeyboardMarkup(keyboard);
        }
    }

}
