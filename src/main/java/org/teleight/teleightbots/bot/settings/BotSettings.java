package org.teleight.teleightbots.bot.settings;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the settings for configuring a Telegram bot.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * BotSettings settings = BotSettings.ofBuilder()
 *                                   .endpointUrl("https://custom.api.url/bot")
 *                                   .updatesLimit(100)
 *                                   .updatesTimeout(300)
 *                                   .silentlyThrowMethodExecution(true)
 *                                   .extensionsEnabled(true)
 *                                   .build();
 * }</pre>
 *
 * @param endpointUrl                  The endpoint URL to be used for connecting with the Telegram Bot API.
 * @param updatesLimit                 The maximum number of updates that the bot will fetch in a single API call.
 * @param updatesTimeout               The timeout, in seconds, for fetching updates from the Telegram API.
 *                                     This is the maximum time the server will wait for a response before timing out.
 * @param silentlyThrowMethodExecution Indicates whether exceptions that occur during method execution should be
 *                                     thrown silently or not.
 * @param extensionsEnabled            Specifies whether bot extensions (additional plugins) should be enabled.
 *                                     When set to {@code true}, extensions are enabled.
 */
@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record BotSettings(
        String endpointUrl,
        int updatesLimit,
        int updatesTimeout,
        boolean silentlyThrowMethodExecution,
        boolean extensionsEnabled
) {

    /**
     * The default Bot API endpoint URL used to connect with the Telegram Bot API.
     */
    public static final String DEFAULT_BOT_API_URL = "https://api.telegram.org/bot";

    /**
     * Default instance of BotSettings with standard configurations:
     * <ul>
     *     <li>{@link #endpointUrl} is set to {@link #DEFAULT_BOT_API_URL}</li>
     *     <li>{@link #updatesLimit} is set to 50</li>
     *     <li>{@link #updatesTimeout} is set to 100</li>
     *     <li>{@link #silentlyThrowMethodExecution} is set to {@code true}</li>
     *     <li>{@link #extensionsEnabled} is set to {@code false}</li>
     * </ul>
     */
    public static final BotSettings DEFAULT = ofBuilder().build();

    /**
     * Returns a new {@link Builder} preconfigured with default settings.
     *
     * @return a new {@link Builder} instance.
     * @see BotSettings#DEFAULT
     */
    public static @NotNull Builder ofBuilder() {
        return new Builder()
                .endpointUrl(DEFAULT_BOT_API_URL)
                .updatesLimit(50)
                .updatesTimeout(100)
                .silentlyThrowMethodExecution(true)
                .extensionsEnabled(false);
    }

}
