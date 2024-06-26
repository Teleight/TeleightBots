package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.MenuButton;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetChatMenuButton(
        @JsonProperty(value = "chat_id")
        String chatId
) implements ApiMethod<MenuButton> {

    public static Builder ofBuilder() {
        return new GetChatMenuButton.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatMenuButton";
    }

    @Override
    public @NotNull MenuButton deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, MenuButton.class);
    }

    public static class Builder {
        private String chatId;

        public GetChatMenuButton.Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public @NotNull GetChatMenuButton build() {
            return new GetChatMenuButton(this.chatId);
        }
    }

}
