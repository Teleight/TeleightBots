package org.teleight.teleightbots.extensions.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExtensionInfoFile(
        @JsonProperty("name")
        String name,

        @JsonProperty("version")
        String version,

        @JsonProperty("parentBot")
        String parentBot,

        @JsonProperty("mainClass")
        String mainClass
) {

    @NotNull
    public static ExtensionInfoFile fromAnnotation(@NotNull ExtensionInfo extensionInfoFile, @NotNull String mainClass) {
        return new ExtensionInfoFile(extensionInfoFile.name(), extensionInfoFile.version(), extensionInfoFile.parentBot(), mainClass);
    }

}
