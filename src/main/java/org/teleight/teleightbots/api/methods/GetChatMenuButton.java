package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.MenuButton;

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
