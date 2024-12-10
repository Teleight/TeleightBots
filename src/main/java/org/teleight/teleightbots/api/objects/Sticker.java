package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Sticker(
        @JsonProperty(value = "file_id", required = true)
        String fileId,

        @JsonProperty(value = "file_unique_id", required = true)
        String fileUniqueId,

        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "width", required = true)
        int width,

        @JsonProperty(value = "height", required = true)
        int height,

        @JsonProperty(value = "is_animated", required = true)
        boolean isAnimated,

        @JsonProperty(value = "is_video", required = true)
        boolean isVideo,

        @JsonProperty(value = "thumbnail")
        @Nullable
        PhotoSize thumbnail,

        @JsonProperty(value = "emoji")
        @Nullable
        String emoji,

        @JsonProperty(value = "set_name")
        @Nullable
        String setName,

        @JsonProperty(value = "premium_annotation")
        @Nullable
        File premiumAnnotation,

        @JsonProperty(value = "mask_position")
        @Nullable
        MaskPosition maskPosition,

        @JsonProperty(value = "custom_emoji_id")
        @Nullable
        String customEmojiId,

        @JsonProperty(value = "needs_repainting")
        boolean needsRepainting,

        @JsonProperty(value = "file_size")
        @Nullable
        Long fileSize
) implements ApiResult {
}
