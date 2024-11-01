package org.teleight.teleightbots.bot.settings;

/**
 * Interface defining common settings for configuring a Telegram bot.
 */
public interface BotSettings {

    /**
     * The default Bot API endpoint URL used to connect with the Telegram Bot API.
     */
    String DEFAULT_BOT_API_URL = "https://api.telegram.org/bot";

    /**
     * Returns the endpoint URL for connecting to the Telegram Bot API.
     * <p>
     * By default, this method returns {@link #DEFAULT_BOT_API_URL}, but can be
     * overridden to provide a custom API URL.
     * </p>
     *
     * @return the Telegram Bot API endpoint URL
     */
    default String endpointUrl() {
        return DEFAULT_BOT_API_URL;
    }

    /**
     * Specifies whether exceptions that occur during bot method execution should be thrown silently.
     *
     * @return {@code true} if exceptions are thrown silently, otherwise {@code false}
     */
    boolean silentlyThrowMethodExecution();

    /**
     * Determines whether bot extensions are enabled.
     *
     * @return {@code true} if extensions are enabled, otherwise {@code false}
     */
    boolean extensionsEnabled();
}
