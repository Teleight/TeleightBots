package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.MenuButton;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetChatMenuButton(
        @JsonProperty(value = "chat_id")
        String chatId
) implements ApiMethod<MenuButton> {

    public static @NotNull Builder ofBuilder() {
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

}
