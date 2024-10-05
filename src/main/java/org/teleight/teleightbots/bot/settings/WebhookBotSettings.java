package org.teleight.teleightbots.bot.settings;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.InputFile;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record WebhookBotSettings(
        String url,
        String path,
        InputFile certificate,
        String ipAddress,
        int maxConnections,
        String[] allowedUpdates,
        boolean dropPendingUpdates,
        String secretToken,

        boolean silentlyThrowMethodExecution
) implements BotSettings {

    public static @NotNull Builder ofBuilder(String url) {
        return new WebhookBotSettings.Builder().url(url);
    }

    public static @NotNull WebhookBotSettings of(String url) {
        return ofBuilder(url).build();
    }

    @Override
    public boolean extensionsEnabled() {
        return false;
    }
}
