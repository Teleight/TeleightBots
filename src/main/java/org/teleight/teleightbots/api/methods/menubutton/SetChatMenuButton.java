package org.teleight.teleightbots.api.methods.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.menubutton.MenuButton;

public record SetChatMenuButton(
        @JsonProperty(value = "chat_id", required = true)
        String chatId,

        @JsonProperty(value = "menu_button", required = true)
        MenuButton menuButton
) implements ApiMethodBoolean {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatMenuButton";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        default @NotNull Builder chatId(long chatId){
            return chatId("" + chatId);
        }

        @NotNull Builder menuButton(@NotNull MenuButton menuButton);

        @NotNull SetChatMenuButton build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private MenuButton menuButton;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder menuButton(@NotNull MenuButton menuButton) {
            this.menuButton = menuButton;
            return this;
        }

        @Override
        public @NotNull SetChatMenuButton build() {
            return new SetChatMenuButton(chatId, menuButton);
        }
    }

}
