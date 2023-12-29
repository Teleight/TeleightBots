package org.teleight.teleightbots.api.objects.keyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record InlineKeyboardButton(
        @JsonProperty("text")
        String text,

        @JsonProperty("url")
        @Nullable
        String url,

        @JsonProperty("callback_data")
        @Nullable
        String callbackData
) implements ApiResult {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder text(@NotNull String text);

        @NotNull Builder url(@NotNull String url);

        @NotNull Builder callbackData(@NotNull String callbackData);

        @NotNull InlineKeyboardButton build();
    }

    static final class BuilderImpl implements Builder {
        private String text;
        private String url;
        private String callbackData;

        @Override
        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder url(@NotNull String url) {
            this.url = url;
            return this;
        }

        @Override
        public @NotNull Builder callbackData(@NotNull String callbackData) {
            this.callbackData = callbackData;
            return this;
        }

        @Override
        public @NotNull InlineKeyboardButton build() {
            return new InlineKeyboardButton(text, url, callbackData);
        }
    }

}
