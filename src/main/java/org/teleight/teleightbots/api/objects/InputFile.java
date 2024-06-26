package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public record InputFile(
        @JsonIgnore
        String telegramFileId,

        @JsonIgnore
        File file,

        @JsonIgnore
        String fileName
) implements ApiResult {

    public static @NotNull InputFile fromFile(@NotNull File file) {
        return new InputFile(null, file, file.getName());
    }

    public static @NotNull InputFile fromFile(@NotNull String filePath) {
        return fromFile(new File(filePath));
    }

    public static @NotNull InputFile fromResource(@NotNull String resource) {
        try {
            final File tempFile = File.createTempFile("resource_", ".tmp");
            final InputStream resourceAsStream = InputFile.class.getClassLoader().getResourceAsStream(resource);
            if (resourceAsStream == null) {
                throw new FileNotFoundException("Resource not found");
            }
            Files.copy(resourceAsStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            resourceAsStream.close();
            return new InputFile(null, tempFile, tempFile.getName());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static @NotNull InputFile fromTelegramId(@NotNull String id) {
        return new InputFile(id, null, null);
    }

}
