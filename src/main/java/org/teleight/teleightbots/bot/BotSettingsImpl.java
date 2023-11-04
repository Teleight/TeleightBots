package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;

public record BotSettingsImpl(
        String endpointUrl,
        int updatesLimit,
        int updatesTimeout
) implements BotSettings {

    public static BotSettingsImpl create(String endpointUrl, int updatesLimit, int updatesTimeout) {
        return new BotSettingsImpl(endpointUrl, updatesLimit, updatesTimeout);
    }

    @Override
    public String endpointUrl() {
        return endpointUrl;
    }

    @Override
    public int updatesLimit() {
        return updatesLimit;
    }


    static class Builder implements BotSettings.Builder {
        private String endpointUrl;
        private int updatesLimit = 100;
        private int updatesTimeout = 50;

        Builder(@NotNull String endpointUrl) {
            this.endpointUrl = endpointUrl;
        }

        @Override
        public BotSettingsImpl.Builder endpointUrl(@NotNull String url) {
            this.endpointUrl = url;
            return this;
        }

        @Override
        public BotSettingsImpl.Builder updatesLimit(int updatesLimit) {
            this.updatesLimit = updatesLimit;
            return this;
        }

        @Override
        public BotSettingsImpl.Builder updatesTimeout(int updatesTimeout) {
            this.updatesTimeout = updatesTimeout;
            return this;
        }

        @Override
        public BotSettingsImpl build() {
            return create(endpointUrl, updatesLimit, updatesTimeout);
        }
    }

}
