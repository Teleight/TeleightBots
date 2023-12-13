package org.teleight.teleightbots.extensions;

public interface ExtensionManager {

    String EXTENSION_FILE = "teleight-extension.json";

    void start();

    void shutdown();

}
