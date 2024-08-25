package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.StickerSet;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetStickerSet(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name
) implements ApiMethod<StickerSet> {

    public static @NotNull Builder ofBuilder(String name) {
        return new GetStickerSet.Builder().name(name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getStickerSet";
    }

    @Override
    public @NotNull StickerSet deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, StickerSet.class);
    }
}
