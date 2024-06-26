package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;
import org.teleight.teleightbots.menu.Menu;

import java.util.UUID;
import java.util.function.BiConsumer;

public record InlineKeyboardButton(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty("url")
        @Nullable
        String url,

        @JsonProperty("callback_data")
        @Nullable
        String callbackData,

        @JsonProperty("web_app")
        @Nullable
        WebAppInfo webApp,

        @JsonIgnore
        Menu destinationMenu,

        @JsonIgnore
        BiConsumer<ButtonPressEvent, User> callback
) implements ApiResult {

    public static @NotNull Builder ofBuilder(String text) {
        return new InlineKeyboardButton.Builder(text);
    }

    public static final class Builder {
        private final String text;
        private String url;
        private String callbackData = UUID.randomUUID().toString();
        private WebAppInfo webApp;
        private Menu destinationMenu;
        private BiConsumer<ButtonPressEvent, User> callback;

        public Builder(String text) {
            this.text = text;
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

        public @NotNull Builder destinationMenu(@NotNull Menu destinationMenu) {
            this.destinationMenu = destinationMenu;
            return this;
        }

        public @NotNull Builder callback(@NotNull BiConsumer<ButtonPressEvent, User> callback) {
            this.callback = callback;
            return this;
        }

        public @NotNull InlineKeyboardButton build() {
            return new InlineKeyboardButton(text, url, callbackData, webApp, destinationMenu, callback);
        }
    }

}
