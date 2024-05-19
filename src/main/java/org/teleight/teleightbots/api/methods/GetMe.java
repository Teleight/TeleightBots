package org.teleight.teleightbots.api.methods;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetMe() implements ApiMethod<User> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMe";
    }

    @Override
    public @NotNull User deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, User.class);
    }

}
