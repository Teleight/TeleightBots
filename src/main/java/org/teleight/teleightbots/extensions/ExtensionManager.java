package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;

public interface ExtensionManager {

    String EXTENSION_FILE = "teleight-extension.json";

    void start();

    void shutdown();

    @NotNull @Unmodifiable Set<Extension> getLoadedExtensions();

}
