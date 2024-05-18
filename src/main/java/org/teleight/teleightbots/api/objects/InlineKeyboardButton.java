package org.teleight.teleightbots.api.objects;

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
        String callbackData,

        @JsonProperty("web_app")
        WebAppInfo webApp
) implements ApiResult {

    public static @NotNull Builder ofBuilder() {
        return new InlineKeyboardButton.Builder();
    }

    public static final class Builder {
        private String text;
        private String url;
        private String callbackData;
        private WebAppInfo webApp;

        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        public @NotNull Builder url(@NotNull String url) {
            this.url = url;
            return this;
        }

        public @NotNull Builder callbackData(@NotNull String callbackData) {
            this.callbackData = callbackData;
            return this;
        }

        public @NotNull Builder webApp(@NotNull WebAppInfo webApp) {
            this.webApp = webApp;
            return this;
        }

        public @NotNull InlineKeyboardButton build() {
            return new InlineKeyboardButton(text, url, callbackData, webApp);
        }
    }

}
