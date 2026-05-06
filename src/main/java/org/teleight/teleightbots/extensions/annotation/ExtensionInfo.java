package org.teleight.teleightbots.extensions.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/// Annotation used to get metadata about an extension.
@Retention(SOURCE)
@Target(TYPE)
@Documented
public @interface ExtensionInfo {

    /// Represents the name of the extension.
    /// @return The name of the extension.
    String name();

    /// Represents the version of the extension.
    /// @return The version of the extension.
    String version();

    /// Represents the parent bot's username of the extension.
    ///
    /// The parent bot is the bot where the extension gets its updates from.
    ///
    /// @return The parent bot's username of the extension.
    String parentBot();

}
