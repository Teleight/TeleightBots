package org.teleight.teleightbots.extensions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.extensions.annotation.ExtensionInfoFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
final class ExtensionManagerImpl implements ExtensionManager {

    private final TelegramBot bot;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path extensionFolder = Path.of("extensions");

    private final Set<LoadedExtension> loadedExtensions = new LinkedHashSet<>();

    ExtensionManagerImpl(@NotNull TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public synchronized void start() {
        if (!Files.exists(extensionFolder)) {
            try {
                Files.createDirectory(extensionFolder);
            } catch (IOException e) {
                TeleightBots.getExceptionManager().handleException(e);
                return;
            }
        }

        final Set<DiscoveredExtension> extensions = discoverExtensions();
        for (DiscoveredExtension discoveredExtension : extensions) {
            final LoadResult loadResult = loadExtension(discoveredExtension);
            if (loadResult instanceof LoadResult.Failure(String errorMessage)) {
                TeleightBots.getExceptionManager().handleException(new IllegalStateException(errorMessage));
                continue;
            }

            if (loadResult instanceof LoadResult.Success(Extension extension, ExtensionClassLoader classLoader)) {
                try {
                    extension.start();
                    loadedExtensions.add(new LoadedExtension(extension, classLoader));
                } catch (Exception e) {
                    TeleightBots.getExceptionManager().handleException(e);
                    closeClassloader(classLoader, discoveredExtension.name());
                }
            }
        }
    }

    @Override
    public @NotNull @Unmodifiable Set<Extension> getLoadedExtensions() {
        final Set<Extension> extensions = new LinkedHashSet<>();
        for (LoadedExtension loadedExtension : loadedExtensions) {
            extensions.add(loadedExtension.extension());
        }
        return Collections.unmodifiableSet(extensions);
    }


    private @NotNull LoadResult loadExtension(@NotNull DiscoveredExtension discoveredExtension) {
        final String extensionName = discoveredExtension.name();
        final String mainClass = discoveredExtension.mainClass();
        final String version = discoveredExtension.version();
        final String botLoader = discoveredExtension.parentBot();

        log.debug("Loading extension: {} (version: {}, bot loader: {})", extensionName, version, botLoader);

        final URL[] urls = new URL[]{discoveredExtension.sourceUrl()};

        final boolean isBotLoaderCorrect = bot.getBotUsername().equalsIgnoreCase(botLoader);
        if (!isBotLoaderCorrect) {
            return new LoadResult.Failure("Invalid bot loader for extension " + extensionName + ". Expected: " + bot.getBotUsername() + ", got: " + botLoader);
        }

        final ExtensionClassLoader extensionClassLoader = new ExtensionClassLoader(urls);

        final Class<?> jarClass;
        try {
            jarClass = Class.forName(mainClass, true, extensionClassLoader);
        } catch (ClassNotFoundException e) {
            TeleightBots.getExceptionManager().handleException(e);
            closeClassloader(extensionClassLoader, extensionName);
            return new LoadResult.Failure("Failed to find main class for extension " + extensionName + ". " + e.getMessage());
        }

        final Class<? extends Extension> extensionClass;
        try {
            extensionClass = jarClass.asSubclass(Extension.class);
        } catch (ClassCastException e) {
            TeleightBots.getExceptionManager().handleException(e);
            closeClassloader(extensionClassLoader, extensionName);
            return new LoadResult.Failure("Failed to cast extension " + extensionName + " to Extension class. " + e.getMessage());
        }

        final Constructor<? extends Extension> constructor;
        try {
            constructor = extensionClass.getDeclaredConstructor(TelegramBot.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            TeleightBots.getExceptionManager().handleException(e);
            closeClassloader(extensionClassLoader, extensionName);
            return new LoadResult.Failure("Failed to find constructor for extension " + extensionName + ". " + e.getMessage());
        }

        Extension extension;
        try {
            extension = constructor.newInstance(bot);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            TeleightBots.getExceptionManager().handleException(e);
            closeClassloader(extensionClassLoader, extensionName);
            return new LoadResult.Failure("Failed to instantiate extension " + extensionName + ". " + e.getMessage());
        }

        return new LoadResult.Success(extension, extensionClassLoader);
    }

    private @NotNull Set<DiscoveredExtension> discoverExtensions() {
        final Set<DiscoveredExtension> extensions = new LinkedHashSet<>();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(extensionFolder, "*.jar")) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                final DiscoveredExtension discovered = discoverFromJar(entry.toFile());
                if (discovered != null) {
                    extensions.add(discovered);
                }
            }
        } catch (IOException e) {
            TeleightBots.getExceptionManager().handleException(e);
        }

        return extensions;
    }

    private @Nullable DiscoveredExtension discoverFromJar(@NotNull File file) {
        try (ZipFile zip = new ZipFile(file)) {
            final ZipEntry entry = zip.getEntry(ExtensionManager.EXTENSION_FILE);
            if (entry == null) {
                throw new IllegalStateException("Missing " + ExtensionManager.EXTENSION_FILE + " in extension " + file.getName() + ".");
            }

            try (InputStreamReader reader = new InputStreamReader(zip.getInputStream(entry))) {
                final ExtensionInfoFile extensionInfo = objectMapper.readValue(reader, ExtensionInfoFile.class);
                return DiscoveredExtension.of(extensionInfo, file.toURI().toURL(), file);
            }
        } catch (IOException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return null;
        }
    }

    @Override
    public synchronized void close() {
        for (LoadedExtension loadedExtension : loadedExtensions) {
            try {
                loadedExtension.extension().shutdown();
            } catch (Exception e) {
                TeleightBots.getExceptionManager().handleException(e);
            }
            closeClassloader(loadedExtension.classLoader(), loadedExtension.extension().getClass().getName());
        }
        loadedExtensions.clear();
    }


    private sealed interface LoadResult {
        record Success(@NotNull Extension extension, @NotNull ExtensionClassLoader classLoader) implements LoadResult {}

        record Failure(@NotNull String errorMessage) implements LoadResult {}
    }

    private record LoadedExtension(@NotNull Extension extension, @NotNull ExtensionClassLoader classLoader) {}

    private void closeClassloader(@NotNull ExtensionClassLoader classLoader, @NotNull String extensionName) {
        try {
            classLoader.close();
        } catch (IOException e) {
            TeleightBots.getExceptionManager().handleException(new IOException("Failed closing classloader for extension " + extensionName + ".", e));
        }
    }
}
