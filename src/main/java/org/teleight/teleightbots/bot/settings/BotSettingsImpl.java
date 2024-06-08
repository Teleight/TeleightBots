package org.teleight.teleightbots.bot.settings;

import org.jetbrains.annotations.NotNull;

public record BotSettingsImpl(
        String endpointUrl,
        int updatesLimit,
        int updatesTimeout,
        boolean silentlyThrowMethodExecution
) implements BotSettings {

    static class Builder implements BotSettings.Builder {
        private String endpointUrl;
        private int updatesLimit = 100;
        private int updatesTimeout = 50;
        private boolean silentlyThrowMethodExecution;

        @Override
        public BotSettings.@NotNull Builder endpointUrl(@NotNull String url) {
            this.endpointUrl = url;
            return this;
        }

        @Override
        public BotSettings.@NotNull Builder updatesLimit(int updatesLimit) {
            this.updatesLimit = updatesLimit;
            return this;
        }

        @Override
        public BotSettings.@NotNull Builder updatesTimeout(int updatesTimeout) {
            this.updatesTimeout = updatesTimeout;
            return this;
        }

        @Override
        public BotSettings.@NotNull Builder silentlyThrowMethodExecution(boolean silentlyThrowMethodExecution) {
            this.silentlyThrowMethodExecution = silentlyThrowMethodExecution;
            return this;
        }

        @Override
        public @NotNull BotSettings build() {
            return new BotSettingsImpl(endpointUrl, updatesLimit, updatesTimeout, silentlyThrowMethodExecution);
        }
    }

}
