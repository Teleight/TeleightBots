package org.teleight.teleightbots.api.methods;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Gifts;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetAvailableGifts() implements ApiMethod<Gifts> {

    public static @NotNull Builder ofBuilder() {
        return new GetAvailableGifts.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getAvailableGifts";
    }

    @Override
    public @NotNull Gifts deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Gifts.class);
    }

}
