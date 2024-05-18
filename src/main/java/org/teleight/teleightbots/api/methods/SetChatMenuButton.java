package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.MenuButton;
import org.teleight.teleightbots.api.objects.MenuButtonDefault;

public record SetChatMenuButton(
        @JsonProperty(value = "chat_id")
        String chatId,

        @JsonProperty(value = "menu_button")
        MenuButton menuButton
) implements ApiMethodBoolean {

    public static @NotNull Builder of() {
        return new SetChatMenuButton.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatMenuButton";
    }

    public static class Builder {
        private String chatId;
        private MenuButton menuButton = new MenuButtonDefault();

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder menuButton(MenuButton menuButton) {
            this.menuButton = menuButton;
            return this;
        }

        public @NotNull SetChatMenuButton build() {
            return new SetChatMenuButton(this.chatId, this.menuButton);
        }
    }

}
