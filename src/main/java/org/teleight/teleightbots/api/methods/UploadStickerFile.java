package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.objects.File;
import org.teleight.teleightbots.api.objects.InputFile;
import org.teleight.teleightbots.api.objects.StickerFormat;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record UploadStickerFile(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "sticker", required = true)
        @NotNull
        InputFile sticker,

        @JsonProperty(value = "sticker_format", required = true)
        @NotNull
        StickerFormat stickerFormat
) implements MultiPartApiMethod<File> {

    public static @NotNull Builder ofBuilder(long userId, InputFile sticker, StickerFormat stickerFormat) {
        return new UploadStickerFile.Builder().userId(userId).sticker(sticker).stickerFormat(stickerFormat);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "uploadStickerFile";
    }

    @Override
    public @NotNull File deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, File.class);
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);
        parameters.put("sticker", sticker);
        parameters.put("sticker_format", stickerFormat);
        return parameters;
    }
}
