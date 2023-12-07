package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.io.File;

public record InputFile(
        @JsonIgnore
        File file,

        @JsonIgnore
        String fileName
) implements ApiResult {

    public static @NotNull InputFile of(File file) {
        return builder().file(file).build();
    }

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder file(@NotNull File file);

        @NotNull Builder fileName(@NotNull String fileName);

        @NotNull InputFile build();
    }

    static final class BuilderImpl implements Builder {
        private File file;
        private String fileName;

        @Override
        public @NotNull Builder file(@NotNull File file) {
            this.file = file;
            return fileName(file.getName());
        }

        @Override
        public @NotNull Builder fileName(@NotNull String fileName) {
            this.fileName = fileName;
            return this;
        }

        @Override
        public @NotNull InputFile build() {
            return new InputFile(file, fileName);
        }
    }

}
