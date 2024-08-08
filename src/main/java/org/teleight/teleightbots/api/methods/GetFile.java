package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.File;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetFile(
        @JsonProperty(value = "file_id", required = true)
        @NotNull
        String fileId
) implements ApiMethod<File> {

    public static @NotNull Builder ofBuilder(String fileId) {
        return new GetFile.Builder().fileId(fileId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getFile";
    }

    @Override
    public @NotNull File deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, File.class);
    }

}
