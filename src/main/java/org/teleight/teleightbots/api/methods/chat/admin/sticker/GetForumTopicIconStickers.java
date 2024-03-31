package org.teleight.teleightbots.api.methods.chat.admin.sticker;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Sticker;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public class GetForumTopicIconStickers implements ApiMethod<Sticker[]> {

    @Override
    public Sticker @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Sticker[].class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getForumTopicIconStickers";
    }

}
