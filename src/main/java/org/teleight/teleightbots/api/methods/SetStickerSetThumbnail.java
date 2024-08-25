package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.StickerFormat;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetStickerSetThumbnail(
        @JsonProperty(value = "name", required = true)
        @NotNull
        String name,

        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty("thumbnail")
        @Nullable
        InputFile thumbnail,

        @JsonProperty(value = "format", required = true)
        @NotNull
        StickerFormat format
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String name, long userId, StickerFormat format) {
        return new SetStickerSetThumbnail.Builder().name(name).userId(userId).format(format);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setStickerSetThumbnail";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("user_id", userId);
        parameters.put("thumbnail", thumbnail);
        parameters.put("format", format);
        return parameters;
    }
}
