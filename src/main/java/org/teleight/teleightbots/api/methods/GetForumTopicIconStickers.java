package org.teleight.teleightbots.api.methods;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Sticker;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetForumTopicIconStickers() implements ApiMethod<Sticker[]> {

    @Override
    public @NotNull String getEndpointURL() {
        return "getForumTopicIconStickers";
    }

    @Override
    public @NotNull Sticker @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, Sticker[].class);
    }

}
