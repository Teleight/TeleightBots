package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.extensions.annotation.ExtensionInfoFile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class DiscoveredExtension {

    private final ExtensionInfoFile extensionInfo;
    final List<URL> files = new ArrayList<>();

    private DiscoveredExtension(ExtensionInfoFile extensionInfo) {
        this.extensionInfo = extensionInfo;
    }

    public static DiscoveredExtension of(ExtensionInfoFile extensionInfo) {
        return new DiscoveredExtension(extensionInfo);
    }

    @NotNull
    public String name() {
        return extensionInfo.name();
    }

    @NotNull
    public String mainClass() {
        return extensionInfo.mainClass();
    }

    @NotNull
    public String version() {
        return extensionInfo.version();
    }

    @NotNull
    public String parentBot() {
        return extensionInfo.parentBot();
    }

}
