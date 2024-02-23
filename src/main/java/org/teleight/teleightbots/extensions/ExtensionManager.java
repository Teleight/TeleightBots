package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;

/**
 * Base interface for the extension manager.
 *
 * @see ExtensionManagerImpl for the default implementation.
 */
public interface ExtensionManager {

    // The name of the extension blueprint file.
    String EXTENSION_FILE = "teleight-extension.json";

    /**
     * Starts the extension manager.
     */
    void start();

    /**
     * Safely shut down the extension manager.
     */
    void shutdown();

    /**
     * Returns a set of the loaded extensions.
     *
     * @return An unmodifiable set of the loaded extensions.
     */
    @NotNull @Unmodifiable Set<Extension> getLoadedExtensions();

}
