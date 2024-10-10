package org.teleight.teleightbots.webhook;

import lombok.Builder;

import java.nio.file.Path;

/**
 * Settings for configuring a local webhook server.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * WebhookServerConfig config = WebhookServerConfig.ofBuilder()
 *           .port(8443)
 *           .keystorePath(Paths.get("/path/to/keystore"))
 *           .keystorePassword("securePassword")
 *           .useHttps(true)
 *           .build();
 * }</pre>
 * </p>
 *
 * @param port             The port on which the webhook server will listen for incoming requests.
 * @param keystorePath     The path to the SSL/TLS keystore file used for secure connections (HTTPS).
 * @param keystorePassword The password for the keystore, used to access the SSL certificates.
 * @param useHttps         Whether the server should use HTTPS for secure communication.
 */
@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder")
public record WebhookServerConfig(
        int port,
        Path keystorePath,
        String keystorePassword,
        boolean useHttps
) {

    /**
     * Default instance of BotSettings with standard configurations:
     * <ul>
     *     <li>{@link #port} is set to 9090</li>
     *     <li>{@link #useHttps} is set to {@code false} (i.e., HTTP is used by default)</li>
     *     <li>{@link #keystorePath} and {@link #keystorePassword} are not set</li>
     * </ul>
     */
    public static final WebhookServerConfig DEFAULT = ofBuilder().build();

    public static class Builder {
        Builder() {
            port = 9090;
            useHttps = false;
        }
    }

}
