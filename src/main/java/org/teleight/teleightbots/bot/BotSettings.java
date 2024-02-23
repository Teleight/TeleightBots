package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;

/**
 * Interface representing bot settings.
 * <p>
 * This interface provides methods to get and set bot settings.
 * It also provides a builder interface for creating bot settings.
 *
 * @see Builder
 */
public interface BotSettings {

    /**
     * Default bot settings.
     */
    BotSettings DEFAULT = BotSettings.of("https://api.telegram.org/bot");

    /**
     * Creates bot settings with the specified endpoint URL and default update limit and timeout.
     *
     * @param endPointUrl the endpoint URL
     * @return the created bot settings
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl) {
        return of(endPointUrl, 100);
    }

    /**
     * Creates bot settings with the specified endpoint URL and update limit, and default timeout.
     *
     * @param endPointUrl  the endpoint URL
     * @param updatesLimit the update limit
     * @return the created bot settings
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit) {
        return of(endPointUrl, updatesLimit, 50);
    }

    /**
     * Creates bot settings with the specified endpoint URL, update limit, and timeout.
     *
     * @param endPointUrl    the endpoint URL
     * @param updatesLimit   the update limit
     * @param updatesTimeout the update timeout
     * @return the created bot settings
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit, int updatesTimeout) {
        return BotSettingsImpl.create(endPointUrl, updatesLimit, updatesTimeout);
    }

    /**
     * Returns a builder for creating bot settings with the specified endpoint URL.
     *
     * @param endPointUrl the endpoint URL
     * @return the builder
     */
    static @NotNull Builder builder(@NotNull String endPointUrl) {
        return new BotSettingsImpl.Builder(endPointUrl);
    }

    /**
     * Returns the endpoint URL.
     *
     * @return the endpoint URL
     */
    String endpointUrl();

    /**
     * Returns the update limit.
     *
     * @return the update limit
     */
    int updatesLimit();

    /**
     * Returns the update timeout.
     *
     * @return the update timeout
     */
    int updatesTimeout();

    /**
     * Interface for building bot settings.
     */
    interface Builder {
        /**
         * Sets the endpoint URL.
         *
         * @param url the endpoint URL
         * @return this builder
         */
        @NotNull Builder endpointUrl(@NotNull String url);

        /**
         * Sets the update limit.
         *
         * @param limit the update limit
         * @return this builder
         */
        @NotNull Builder updatesLimit(int limit);

        /**
         * Sets the update timeout.
         *
         * @param timeout the update timeout
         * @return this builder
         */
        @NotNull Builder updatesTimeout(int timeout);

        /**
         * Builds the bot settings.
         *
         * @return the built bot settings
         */
        @NotNull BotSettings build();
    }

}
