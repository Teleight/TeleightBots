package org.teleight.teleightbots.updateprocessor.webhook;

import java.nio.file.Path;

public record WebhookServerConfig(
        String port,
        Path keystorePath,
        String keystorePassword,
        boolean useHttps
) {
}
