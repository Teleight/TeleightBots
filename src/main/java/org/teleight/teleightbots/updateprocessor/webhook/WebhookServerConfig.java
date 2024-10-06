package org.teleight.teleightbots.updateprocessor.webhook;

import lombok.Builder;

import java.nio.file.Path;

@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder")
public record WebhookServerConfig(
        int port,
        Path keystorePath,
        String keystorePassword,
        boolean useHttps
) {

    public static final WebhookServerConfig DEFAULT = ofBuilder().build();

    public static class Builder {
        Builder() {
            port = 9090;
            useHttps = false;
        }
    }

}
