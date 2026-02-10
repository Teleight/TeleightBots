package org.teleight.teleightbots.api.methods;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record RemoveMyProfilePhoto() implements ApiMethodBoolean {

    @Override
    public @NotNull String getEndpointURL() {
        return "removeMyProfilePhoto";
    }

}
