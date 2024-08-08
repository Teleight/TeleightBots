package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.MenuButton;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetChatMenuButton(
        @JsonProperty(value = "chat_id")
        String chatId,

        @JsonProperty(value = "menu_button")
        MenuButton menuButton
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder() {
        return new SetChatMenuButton.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setChatMenuButton";
    }

}
