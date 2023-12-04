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

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder {
        @NotNull Builder text(@NotNull String text);

        @NotNull Builder url(@NotNull String url);

        @NotNull Builder destinationMenu(@NotNull Menu destinationMenu);

        @NotNull Builder callback(@NotNull BiConsumer<ButtonPressEvent, User> callback);

        @NotNull MenuButton build();
    }

    static final class BuilderImpl implements Builder {
        private String text;
        private String url;
        private final String callbackData = UUID.randomUUID().toString();
        private Menu destinationMenu;
        private BiConsumer<ButtonPressEvent,User> callback;

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
        public @NotNull Builder destinationMenu(@NotNull Menu destinationMenu) {
            this.destinationMenu = destinationMenu;
            return this;
        }

        @Override
        public @NotNull Builder callback(@NotNull BiConsumer<ButtonPressEvent,User> callback) {
            this.callback = callback;
            return this;
        }

        @Override
        public @NotNull MenuButton build() {
            return new MenuButton(text, url, callbackData, destinationMenu, callback);
        }
    }

}
