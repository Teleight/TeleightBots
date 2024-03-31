package org.teleight.teleightbots.extensions.annotation;

/**
 * Annotation used to get metadata about an extension.
 */
public @interface ExtensionInfo {

    /**
     * Represents the name of the extension.
     * @return The name of the extension.
     */
    String name();

    /**
     * Represents the version of the extension.
     * @return The version of the extension.
     */
    String version();

    /**
     * Represents the parent bot's username of the extension.
     * <p>
     * The parent bot is the bot where the extension gets its updates from.
     * </p>
     * @return The parent bot's username of the extension.
     */
    String parentBot();

}
