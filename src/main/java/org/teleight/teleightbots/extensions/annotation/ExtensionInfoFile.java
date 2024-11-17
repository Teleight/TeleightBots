package org.teleight.teleightbots.extensions.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExtensionInfoFile(
        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "version")
        String version,

        @JsonProperty(value = "parentBot")
        String parentBot,

        @JsonProperty(value = "mainClass")
        String mainClass
) {

    @NotNull
    public static ExtensionInfoFile fromAnnotation(@NotNull ExtensionInfo extensionInfoFile, @NotNull String mainClass) {
        return new ExtensionInfoFile(extensionInfoFile.name(), extensionInfoFile.version(), extensionInfoFile.parentBot(), mainClass);
    }

}
