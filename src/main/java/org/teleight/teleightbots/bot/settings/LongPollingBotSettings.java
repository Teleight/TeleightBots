package org.teleight.teleightbots.bot.settings;

import lombok.Builder;

import java.util.List;

/**
 * Represents the settings for configuring a long polling Telegram bot.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * LongPollingBotSettings settings = LongPollingBotSettings.ofBuilder()
 *         .endpointUrl("https://custom.api.url/bot")
 *         .updatesLimit(100)
 *         .updatesTimeout(300)
 *         .silentlyThrowMethodExecution(true)
 *         .extensionsEnabled(true)
 *         .build();
 * }</pre>
 *
 * @param endpointUrl                  The endpoint URL to be used for connecting with the Telegram Bot API.
 * @param updatesLimit                 The maximum number of updates that the bot will fetch in a single API call.
 * @param updatesTimeout               The timeout, in seconds, for fetching updates from the Telegram API.
 *                                     This is the maximum time the server will wait for a response before timing out.
 * @param silentlyThrowMethodExecution Whether to silently throw method execution errors.
 * @param extensionsEnabled            Whether bot extensions are enabled.
 * @param allowedUpdates               A list of update types that the bot is allowed to receive.
 *                                     If null or empty, the bot will receive all update types except
 *                                     "chat_member", "message_reaction", and "message_reaction_count"
 */
@Builder(builderClassName = "Builder", builderMethodName = "ofBuilder", toBuilder = true)
public record LongPollingBotSettings(
        String endpointUrl,
        int updatesLimit,
        int updatesTimeout,
        boolean silentlyThrowMethodExecution,
        boolean extensionsEnabled,
        List<String> allowedUpdates
) implements BotSettings {

    /**
     * Default instance of BotSettings with standard configurations
     */
    public static final LongPollingBotSettings DEFAULT = ofBuilder()
            .endpointUrl(DEFAULT_BOT_API_URL)
            .updatesLimit(50)
            .updatesTimeout(100)
            .silentlyThrowMethodExecution(true)
            .extensionsEnabled(false)
            .allowedUpdates(null)
            .build();

}
