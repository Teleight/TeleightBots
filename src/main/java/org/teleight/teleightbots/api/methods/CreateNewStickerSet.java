package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputSticker;
import org.teleight.teleightbots.api.objects.StickerType;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record CreateNewStickerSet(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "stickers", required = true)
        @NotNull
        InputSticker[] stickers,

        @JsonProperty(value = "sticker_type")
        StickerType stickerType,

        @JsonProperty(value = "needs_repainting")
        boolean needsRepainting
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId, String name, String title, InputSticker[] stickers) {
        return new CreateNewStickerSet.Builder().userId(userId).name(name).title(title).stickers(stickers);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createNewStickerSet";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);
        parameters.put("name", name);
        parameters.put("title", title);
        parameters.put("stickers", stickers);
        parameters.put("sticker_type", stickerType);
        parameters.put("needs_repainting", needsRepainting);
        return parameters;
    }
}
