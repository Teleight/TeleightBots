package org.teleight.teleightbots.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.util.UUID;

public record MenuButton(
        @JsonProperty("text")
        String text,

        @JsonProperty("callback_data")
        String callbackData
) implements ApiResult {

    public static final String MENU_ID_PARAM = "menu_id=";

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder {
        @NotNull Builder text(@NotNull String text);

        @NotNull Builder destinationMenu(@NotNull Menu destinationMenu);

        @NotNull MenuButton build();
    }

    static final class BuilderImpl implements Builder {
        private String text;
        private String callbackData = UUID.randomUUID().toString();

        @Override
        public @NotNull Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public @NotNull Builder destinationMenu(@NotNull Menu destinationMenu) {
            callbackData = UUID.randomUUID() + "&" + MENU_ID_PARAM + destinationMenu.getMenuId();
            return this;
        }

        @Override
        public @NotNull MenuButton build() {
            return new MenuButton(text, callbackData);
        }
    }

}
