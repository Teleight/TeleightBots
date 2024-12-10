package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetCustomEmojiStickerSetThumbnail(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "custom_emoji_id")
        @Nullable
        String custom_emoji_id
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String name) {
        return new SetCustomEmojiStickerSetThumbnail.Builder().name(name);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setCustomEmojiStickerSetThumbnail";
    }

}
