package org.teleight.teleightbots.api.methods;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.User;

public record GetMe() implements ApiMethod<User> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMe";
    }

}
