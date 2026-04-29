package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.KeyboardButton;
import org.teleight.teleightbots.api.objects.PreparedKeyboardButton;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SavePreparedKeyboardButton(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "keyboard_button", required = true)
        KeyboardButton keyboardButton
) implements ApiMethod<PreparedKeyboardButton> {

    public static @NotNull Builder ofBuilder(long userId, KeyboardButton keyboardButton) {
        return new SavePreparedKeyboardButton.Builder()
                .userId(userId)
                .keyboardButton(keyboardButton);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "savePreparedKeyboardButton";
    }

    @Override
    public @NotNull PreparedKeyboardButton deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, PreparedKeyboardButton.class);
    }
}
