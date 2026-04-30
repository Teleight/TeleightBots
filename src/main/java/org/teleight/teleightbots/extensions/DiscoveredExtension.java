package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.extensions.annotation.ExtensionInfoFile;

import java.io.File;
import java.net.URL;

final class DiscoveredExtension {

    private final ExtensionInfoFile extensionInfo;
    private final URL sourceUrl;
    private final File sourceFile;

    private DiscoveredExtension(@NotNull ExtensionInfoFile extensionInfo, @NotNull URL sourceUrl, @NotNull File sourceFile) {
        this.extensionInfo = extensionInfo;
        this.sourceUrl = sourceUrl;
        this.sourceFile = sourceFile;
    }

    public @NotNull static DiscoveredExtension of(@NotNull ExtensionInfoFile extensionInfo, @NotNull URL sourceUrl, @NotNull File sourceFile) {
        return new DiscoveredExtension(extensionInfo, sourceUrl, sourceFile);
    }

    public @NotNull String name() {
        return extensionInfo.name();
    }

    public @NotNull String mainClass() {
        return extensionInfo.mainClass();
    }

    public @NotNull String version() {
        return extensionInfo.version();
    }

    public @NotNull String parentBot() {
        return extensionInfo.parentBot();
    }

    public @NotNull URL sourceUrl() {
        return sourceUrl;
    }

    public @NotNull File sourceFile() {
        return sourceFile;
    }

}
