package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.extensions.annotation.ExtensionInfoFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

final class DiscoveredExtension {

    private final ExtensionInfoFile extensionInfo;
    final List<URL> files = new ArrayList<>();

    private DiscoveredExtension(@NotNull ExtensionInfoFile extensionInfo) {
        this.extensionInfo = extensionInfo;
    }

    public @NotNull static DiscoveredExtension of(@NotNull ExtensionInfoFile extensionInfo) {
        return new DiscoveredExtension(extensionInfo);
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

}
