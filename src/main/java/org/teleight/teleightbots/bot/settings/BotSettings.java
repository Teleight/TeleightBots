package org.teleight.teleightbots.bot.settings;

import org.jetbrains.annotations.NotNull;

/**
 * Interface representing the settings for a bot.
 * <p>
 * This interface provides methods to retrieve and configure bot settings,
 * such as the endpoint URL, update limit, and update timeout.
 * Additionally, it offers a builder interface for creating customized bot settings.
 *
 * @see Builder
 */
public sealed interface BotSettings permits BotSettingsImpl {

    /**
     * Default Bot API endpoint
     */
    String DEFAULT_BOT_API_URL = "https://api.telegram.org/bot";

    /**
     * Default bot settings with the default endpoint URL.
     */
    BotSettings DEFAULT = BotSettings.of(DEFAULT_BOT_API_URL);

    /**
     * Creates bot settings with the specified endpoint URL and default values for update limit and timeout.
     *
     * @param endPointUrl the endpoint URL to be used for the bot
     * @return the created bot settings instance
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl) {
        return of(endPointUrl, 100);
    }

    /**
     * Creates bot settings with the specified endpoint URL and update limit, using a default timeout.
     *
     * @param endPointUrl  the endpoint URL to be used for the bot
     * @param updatesLimit the maximum number of updates to be fetched in a single call
     * @return the created bot settings instance
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit) {
        return of(endPointUrl, updatesLimit, 50);
    }

    /**
     * Creates bot settings with the specified endpoint URL, update limit, and timeout.
     *
     * @param endPointUrl    the endpoint URL to be used for the bot
     * @param updatesLimit   the maximum number of updates to be fetched in a single call
     * @param updatesTimeout the timeout in seconds for fetching updates
     * @return the created bot settings instance
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit, int updatesTimeout) {
        return of(endPointUrl, updatesLimit, updatesTimeout, false);
    }

    /**
     * Creates bot settings with the specified endpoint URL, update limit, timeout, and silent method throwing
     *
     * @param endPointUrl                  the endpoint URL to be used for the bot
     * @param updatesLimit                 the maximum number of updates to be fetched in a single call
     * @param updatesTimeout               the timeout in seconds for fetching updates
     * @param silentlyThrowMethodExecution whether the method execution should throw exceptions silently.
     * @return the created bot settings instance
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit, int updatesTimeout, boolean silentlyThrowMethodExecution) {
        return of(endPointUrl, updatesLimit, updatesTimeout, silentlyThrowMethodExecution, false);
    }

    /**
     * Creates bot settings with the specified endpoint URL,
     * update limit, timeout and whether extensions are enabled or not
     *
     * @param endPointUrl                  the endpoint URL to be used for the bot
     * @param updatesLimit                 the maximum number of updates to be fetched in a single call
     * @param updatesTimeout               the timeout in seconds for fetching updates
     * @param extensionsEnabled            whether extensions should be enabled or not
     * @param silentlyThrowMethodExecution whether the method execution should throw exceptions silently.
     * @return the created bot settings instance
     */
    static @NotNull BotSettings of(@NotNull String endPointUrl, int updatesLimit, int updatesTimeout, boolean silentlyThrowMethodExecution, boolean extensionsEnabled) {
        return ofBuilder(endPointUrl).updatesLimit(updatesLimit).updatesTimeout(updatesTimeout).silentlyThrowMethodExecution(silentlyThrowMethodExecution).extensionsEnabled(extensionsEnabled).build();
    }

    /**
     * Returns a builder for creating bot settings with the specified endpoint URL.
     *
     * @param endPointUrl the endpoint URL to be used for the bot
     * @return the builder instance for chaining configuration methods
     */
    static @NotNull Builder ofBuilder(@NotNull String endPointUrl) {
        return new BotSettingsImpl.Builder().endpointUrl(endPointUrl);
    }

    /**
     * Returns the configured endpoint URL.
     * <p>
     * The endpoint URL is the base URL used by the bot to connect to the API
     *
     * @return the endpoint URL as a string, never null
     */
    String endpointUrl();

    /**
     * Returns the configured update limit.
     * <p>
     * The update limit specifies the maximum number of updates that the bot can fetch
     * in a single call to the API
     *
     * @return the update limit as an integer, representing the maximum number of updates per call
     */
    int updatesLimit();

    /**
     * Returns the configured update timeout.
     * <p>
     * The update timeout specifies the maximum time in seconds that the bot will wait
     * for updates from the API.
     *
     * @return the update timeout in seconds as an integer, representing the wait time for updates
     */
    int updatesTimeout();

    /**
     * Indicates whether the method execution should throw exceptions silently.
     *
     * @return true if method execution should throw silently, false otherwise
     */
    boolean silentlyThrowMethodExecution();

    /**
     * Returns whether extensions are enabled or not
     * <p>
     * Extensions are separate pieces of software that connect to a specific bot instance
     *
     * @return true if extensions are enabled, false otherwise
     * @see org.teleight.teleightbots.extensions.Extension
     * @see org.teleight.teleightbots.extensions.ExtensionManager
     */
    boolean extensionsEnabled();

    /**
     * Interface for building {@link BotSettings} instances.
     */
    interface Builder {
        /**
         * Sets the endpoint URL.
         *
         * @param url the endpoint URL to be used for the bot
         * @return this builder instance for chaining configuration methods
         */
        @NotNull
        Builder endpointUrl(@NotNull String url);

        /**
         * Sets the update limit.
         *
         * @param limit the maximum number of updates to be fetched in a single call
         * @return this builder instance for chaining configuration methods
         */
        @NotNull
        Builder updatesLimit(int limit);

        /**
         * Sets the update timeout.
         *
         * @param timeout the timeout in seconds for fetching updates
         * @return this builder instance for chaining configuration methods
         */
        @NotNull
        Builder updatesTimeout(int timeout);

        /**
         * Sets whether the method execution should throw exceptions silently.
         * If set to true, exceptions during method execution will not be thrown.
         *
         * @param silentlyThrowMethodExecution true if exceptions should be thrown silently, false otherwise
         * @return this builder instance for chaining configuration methods
         */
        @NotNull
        Builder silentlyThrowMethodExecution(boolean silentlyThrowMethodExecution);

        /**
         * Enables or disable bot extensions.
         *
         * @param extensionsEnabled whether extensions should be enabled or not
         * @return this builder instance for chaining configuration methods
         */
        @NotNull
        Builder extensionsEnabled(boolean extensionsEnabled);

        /**
         * Builds and returns the configured {@link BotSettings} instance.
         *
         * @return the created {@link BotSettings} instance
         */
        @NotNull
        BotSettings build();
    }

}
