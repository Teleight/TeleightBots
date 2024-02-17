package org.teleight.teleightbots.api.methods.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.menubutton.MenuButton;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatMenuButton(
        @JsonProperty(value = "chat_id", required = true)
        String chatId
) implements ApiMethod<MenuButton> {

    public static @NotNull GetChatMenuButton of(long chatId) {
        return of("" + chatId);
    }

    public static @NotNull GetChatMenuButton of(@NotNull String chatId) {
        return new GetChatMenuButton(chatId);
    }

    @Override
    public @NotNull MenuButton deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, MenuButton.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMenuButton";
    }

}
