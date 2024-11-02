package org.teleight.teleightbots.extensions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.bot.TelegramBot;

import java.io.Closeable;
import java.util.Set;

/**
 * Base interface for the extension manager.
 *
 * @see ExtensionManagerImpl for the default implementation.
 */
public sealed interface ExtensionManager extends Closeable permits ExtensionManagerImpl {

    @ApiStatus.Internal
    static @NotNull ExtensionManager newExtensionManager(@NotNull TelegramBot bot) {
        return new ExtensionManagerImpl(bot);
    }

    // The name of the extension blueprint file.
    String EXTENSION_FILE = "teleight-extension.json";

    /**
     * Starts the extension manager.
     */
    void start();

    /**
     * Returns a set of the loaded extensions.
     *
     * @return An unmodifiable set of the loaded extensions.
     */
    @NotNull @Unmodifiable Set<Extension> getLoadedExtensions();

}
