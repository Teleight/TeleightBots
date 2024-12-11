package org.teleight.teleightbots.webhook;

import io.jooby.SslOptions;
import lombok.Builder;
import org.jetbrains.annotations.Nullable;

/**
 * Settings for configuring a local webhook server.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * WebhookServerConfig config = WebhookServerConfig.ofBuilder()
 *           .port(8443)
 *           .useHttps(true)
 *           .build();
 * }</pre>
 *
 * @param port             The port on which the webhook server will listen for incoming requests.
 * @param useHttps         Whether the server should use HTTPS for secure communication.
 * @param sslOptions       SSL options for enabling HTTPS
 */
@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder")
public record WebhookServerConfig(
        @Nullable String host,
        int port,
        @Nullable SslOptions sslOptions,
        boolean useHttps
) {

    /**
     * Default instance of BotSettings with standard configurations:
     * <ul>
     *     <li>{@link #port} is set to 8443</li>
     *     <li>{@link #useHttps} is set to {@code false} (i.e., HTTP is used by default)</li>
     *     <li>{@link #sslOptions} is not set</li>
     * </ul>
     */
    public static final WebhookServerConfig DEFAULT = ofBuilder().build();

    public static class Builder {
        Builder() {
            host = null;
            port = 8443;
            useHttps = false;
        }
    }

}
