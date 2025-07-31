package org.teleight.teleightbots.api.methods;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.StarAmount;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetMyStarBalance() implements ApiMethod<StarAmount> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyStarBalance";
    }

    @Override
    public @NotNull StarAmount deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, StarAmount.class);
    }

}
