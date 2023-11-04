package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;

public interface BotSettings {

    BotSettings DEFAULT = BotSettings.of("https://api.telegram.org/bot");

    static BotSettings of(@NotNull String endPointUrl) {
        return of(endPointUrl, 100);
    }

    static BotSettings of(@NotNull String endPointUrl, int updatesLimit) {
        return of(endPointUrl, updatesLimit, 50);
    }

    static BotSettings of(@NotNull String endPointUrl, int updatesLimit, int updatesTimeout) {
        return BotSettingsImpl.create(endPointUrl, updatesLimit, updatesTimeout);
    }

    static Builder builder(@NotNull String endPointUrl) {
        return new BotSettingsImpl.Builder(endPointUrl);
    }


    String endpointUrl();

    int updatesLimit();

    int updatesTimeout();


    interface Builder {
        Builder endpointUrl(@NotNull String url);

        Builder updatesLimit(int limit);

        Builder updatesTimeout(int timeout);

        BotSettings build();
    }

}
