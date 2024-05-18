package org.teleight.teleightbots.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.event.keyboard.ButtonPressEvent;

import java.util.UUID;
import java.util.function.BiConsumer;

public record MenuButton(
        @JsonProperty("text")
        String text,

        @JsonProperty("url")
        String url,

        @JsonProperty("callback_data")
        String callbackData,

        @JsonIgnore
        Menu destinationMenu,

        @JsonIgnore
        BiConsumer<ButtonPressEvent, User> callback
) implements ApiResult {

    public static @NotNull Builder ofBuilder() {
        return new MenuButton.Builder();
    }

    public static final class Builder {
        private String text;
        private String url;
        private final String callbackData = UUID.randomUUID().toString();
        private Menu destinationMenu;
        private BiConsumer<ButtonPressEvent, User> callback;

        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        public @NotNull Builder url(@NotNull String url) {
            this.url = url;
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

        public @NotNull MenuButton build() {
            return new MenuButton(text, url, callbackData, destinationMenu, callback);
        }
    }

}
