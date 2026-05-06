package org.teleight.teleightbots.webhook;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/// Settings for configuring an internal webhook server.
///
/// Example usage:
///
/// ```
/// WebhookServerConfig config = WebhookServerConfig.ofBuilder()
///           .port(8443)
///           .keystorePath(Paths.get("/path/to/keystore"))
///           .keystorePassword("securePassword")
///           .useHttps(true)
///           .build();
/// ```
///
/// @param port             The port on which the webhook server will listen for incoming requests.
/// @param keystorePath     The path to the SSL/TLS keystore file used for secure connections (HTTPS).
/// @param keystorePassword The password for the keystore, used to access the SSL certificates.
/// @param useHttps         Whether the server should use HTTPS for secure communication.
@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder")
public record WebhookServerConfig(
        @Nullable String host,
        int port,
        @Nullable Path keystorePath,
        @Nullable String keystorePassword,
        boolean useHttps
) {

    /// Default instance of WebhookServerConfig with standard configurations:
    ///
    ///   - [#host] is set to `null`, localhost
    ///   - [#port] is set to 8443
    ///   - [#useHttps] is set to `false` (HTTP is used by default)
    ///   - [#keystorePath] and [#keystorePassword] are not set
    public static final WebhookServerConfig DEFAULT = ofBuilder().build();

    public static class Builder {
        Builder() {
            host = null;
            port = 8443;
            useHttps = false;
        }
    }

}
