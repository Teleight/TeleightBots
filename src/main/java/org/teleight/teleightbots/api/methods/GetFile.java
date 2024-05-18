package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.File;

public record GetFile(
        @JsonProperty(value = "file_id", required = true)
        @NotNull
        String fileId
) implements ApiMethod<File> {

    public static @NotNull GetFile of(String fileId) {
        return new GetFile(fileId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getFile";
    }

}
