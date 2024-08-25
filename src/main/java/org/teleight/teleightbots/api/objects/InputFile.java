package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.serializers.InputFileSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Represents a file that can be used as an input for API requests.
 * The file can be provided from various sources such as a local file system,
 * a resource from the classpath, a file identified by a Telegram ID stored on the Telegram servers, or a URL.
 */
@JsonSerialize(using = InputFileSerializer.class)
public record InputFile(
        @JsonIgnore
        String id,

        @JsonIgnore
        File file,

        @JsonIgnore
        String fileName
) implements ApiResult {

    /**
     * Creates an {@code InputFile} instance from a {@link File} object.
     *
     * @param file the file to be used as input
     * @return a new {@code InputFile} instance containing the provided file
     * @throws NullPointerException if the provided {@code file} is null
     */
    public static @NotNull InputFile fromFile(@NotNull File file) {
        return new InputFile(null, file, file.getName());
    }

    /**
     * Creates an {@code InputFile} instance from a file path as {@code String}.
     *
     * @param filePath the path to the file
     * @return a new {@code InputFile} instance containing the file located at the specified path
     * @throws NullPointerException if the provided {@code filePath} is null
     */
    public static @NotNull InputFile fromFile(@NotNull String filePath) {
        return fromFile(new File(filePath));
    }

    /**
     * Creates an {@code InputFile} instance from a file path as {@code Path}.
     *
     * @param filePath the path to the file
     * @return a new {@code InputFile} instance containing the file located at the specified path
     * @throws NullPointerException if the provided {@code filePath} is null
     */
    public static @NotNull InputFile fromFile(@NotNull Path filePath) {
        return fromFile(filePath.toFile());
    }

    /**
     * Creates an {@code InputFile} instance from a resource on the classpath.
     *
     * <p>The resource will be copied to a temporary file, which will be used as the input.</p>
     *
     * @param resource the path to the resource within the classpath
     * @return a new {@code InputFile} instance containing the resource as a file
     * @throws IllegalStateException if an I/O error occurs while accessing the resource
     * @throws NullPointerException if the provided {@code resource} is null
     */
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an {@code InputFile} instance from a Telegram file ID or URL.
     *
     * @param id the Telegram file ID, or URL
     * @return a new {@code InputFile} instance containing the Telegram file ID
     * @throws NullPointerException if the provided {@code id} is null
     */
    public static @NotNull InputFile fromId(@NotNull String id) {
        return new InputFile(id, null, null);
    }

}
