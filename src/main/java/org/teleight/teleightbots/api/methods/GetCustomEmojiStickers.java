package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Sticker;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetCustomEmojiStickers(
        @JsonProperty(value = "custom_emoji_ids", required = true)
        @NotNull
        String[] customEmojiIds
) implements ApiMethod<Sticker[]> {

    public static @NotNull Builder ofBuilder(String[] customEmojiIds) {
        return new GetCustomEmojiStickers.Builder().customEmojiIds(customEmojiIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getCustomEmojiStickers";
    }

    @Override
    public Sticker @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Sticker[].class);
    }
}
