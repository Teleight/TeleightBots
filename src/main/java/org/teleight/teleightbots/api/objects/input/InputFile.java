package org.teleight.teleightbots.api.objects.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.io.File;

@Builder
public record InputFile(
        @JsonIgnore
        File file,

        @JsonIgnore
        String fileName
) implements ApiResult {

    public static @NotNull InputFile of(File file) {
        return builder().file(file).build();
    }

}
