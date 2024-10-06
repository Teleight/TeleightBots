package org.teleight.teleightbots.bot.settings;

import lombok.Builder;

/**
 * Represents the settings for configuring a Telegram bot.
 * <p>
 * The builder method returns a new {@link Builder} preconfigured with default settings.
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
 */
@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder")
public record LongPollingBotSettings(
        String endpointUrl,
        int updatesLimit,
        int updatesTimeout,
        boolean silentlyThrowMethodExecution,
        boolean extensionsEnabled
) implements BotSettings {

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
    public static final LongPollingBotSettings DEFAULT = ofBuilder().build();

    public static class Builder {
        Builder() {
            endpointUrl = DEFAULT_BOT_API_URL;
            updatesLimit = 50;
            updatesTimeout = 100;
            silentlyThrowMethodExecution = true;
            extensionsEnabled = false;
        }
    }

}
