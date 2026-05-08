package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputMediaSticker(
        @JsonProperty(value = "media", required = true)
        @NotNull
        InputFile media,

        @JsonProperty(value = "emoji")
        @Nullable
        String emoji
) implements InputPollOptionMedia {

    public static @NotNull Builder ofBuilder(InputFile media) {
        return new InputMediaSticker.Builder().media(media);
    }

    @Override
    public String type() {
        return "sticker";
    }

}
