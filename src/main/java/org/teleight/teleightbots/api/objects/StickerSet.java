package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record StickerSet(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "sticker_type", required = true)
        @NotNull
        String stickerType,

        @JsonProperty(value = "stickers", required = true)
        @NotNull
        Sticker[] stickers,

        @JsonProperty("thumbnail")
        @Nullable
        PhotoSize thumbnail
) implements ApiResult {
}
