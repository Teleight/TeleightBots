package org.teleight.teleightbots.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.extensions.annotation.ExtensionInfoFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtensionManagerImpl implements ExtensionManager {

    private final Bot bot;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File extensionFolder = new File("extensions");

    private final Set<Extension> loadedExtensions = new HashSet<>();

    public ExtensionManagerImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void start() {
        if(!extensionFolder.exists()) {
            extensionFolder.mkdirs();
        }

        final Set<DiscoveredExtension> extensions = discoverExtensions();
        for (DiscoveredExtension discoveredExtension : extensions) {
            final LoadResult loadExtension = loadExtension(discoveredExtension);
            if(loadExtension instanceof LoadResult.Success success) {
                try {
                    final Extension extension = success.extension();
                    extension.start();

                    loadedExtensions.add(extension);
                }catch (Exception e){
                    TeleightBots.getExceptionManager().handleException(e);
                }
            }
        }
    }

    @Override
    public @NotNull @Unmodifiable Set<Extension> getLoadedExtensions() {
        return Collections.unmodifiableSet(loadedExtensions);
    }


    private @NotNull LoadResult loadExtension(@NotNull DiscoveredExtension discoveredExtension) {
        final String extensionName = discoveredExtension.name();
        final String mainClass = discoveredExtension.mainClass();
        final String version = discoveredExtension.version();
        final String botLoader = discoveredExtension.parentBot();
        final URL[] urls = discoveredExtension.files.toArray(URL[]::new);


        final boolean isBotLoaderCorrect = bot.getBotUsername().equalsIgnoreCase(botLoader);
        if(!isBotLoaderCorrect) {
            return new LoadResult.InvalidBotLoader("Invalid bot loader for extension " + extensionName + ". Expected: " + bot.getBotUsername() + ", got: " + botLoader);
        }

        final ExtensionClassLoader extensionClassLoader = new ExtensionClassLoader(urls);

        final Class<?> jarClass;
        try {
            jarClass = Class.forName(mainClass, true, extensionClassLoader);
        } catch (ClassNotFoundException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return new LoadResult.InvalidBotLoader("Failed to find main class for extension " + extensionName + ". " + e.getMessage());
        }

        final Class<? extends Extension> extensionClass;
        try {
            extensionClass = jarClass.asSubclass(Extension.class);
        } catch (ClassCastException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return new LoadResult.InvalidBotLoader("Failed to cast extension " + extensionName + " to Extension class. " + e.getMessage());
        }

        final Constructor<? extends Extension> constructor;
        try {
            constructor = extensionClass.getDeclaredConstructor(Bot.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return new LoadResult.InvalidBotLoader("Failed to find constructor for extension " + extensionName + ". " + e.getMessage());
        }

        Extension extension = null;
        try {
            extension = constructor.newInstance(bot);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return new LoadResult.InvalidBotLoader("Failed to instantiate extension " + extensionName + ". " + e.getMessage());
        }

        return new LoadResult.Success(extension);
    }

    private @NotNull Set<DiscoveredExtension> discoverExtensions() {
        final Set<DiscoveredExtension> extensions = new LinkedHashSet<>();
        final File[] fileList = extensionFolder.listFiles();
        if (fileList == null) {
            return extensions;
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                continue;
            }
            final boolean isJarFile = file.getName().endsWith(".jar");
            if (!isJarFile) {
                continue;
            }
            final DiscoveredExtension extension = discoverFromJar(file);
            if (extension == null) {
                continue;
            }
            extensions.add(extension);
        }
        return extensions;
    }


    private @Nullable DiscoveredExtension discoverFromJar(@NotNull File file) {
        try (ZipFile zip = new ZipFile(file)) {
            final ZipEntry entry = zip.getEntry(ExtensionManager.EXTENSION_FILE);
            if (entry == null) {
                throw new IllegalStateException("Missing " + ExtensionManager.EXTENSION_FILE + " in extension " + file.getName() + ".");
            }

            try(InputStreamReader reader = new InputStreamReader(zip.getInputStream(entry));) {
                final ExtensionInfoFile extensionInfo = objectMapper.readValue(reader, ExtensionInfoFile.class);
                final DiscoveredExtension extension = DiscoveredExtension.of(extensionInfo);
                extension.files.add(file.toURI().toURL());
                return extension;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        for (Extension extension : loadedExtensions) {
            try{
                extension.shutdown();
            } catch (Exception e) {
                TeleightBots.getExceptionManager().handleException(e);
            }
        }
    }


    private sealed interface LoadResult {
        record Success(@NotNull Extension extension) implements LoadResult {}

        record InvalidBotLoader(@NotNull String errorMessage) implements LoadResult {}
    }

}
