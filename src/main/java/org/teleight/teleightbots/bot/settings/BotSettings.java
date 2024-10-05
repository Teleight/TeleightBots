package org.teleight.teleightbots.bot.settings;

public interface BotSettings {

    /**
     * The default Bot API endpoint URL used to connect with the Telegram Bot API.
     */
    String DEFAULT_BOT_API_URL = "https://api.telegram.org/bot";

    default String endpointUrl() {
        return DEFAULT_BOT_API_URL;
    }

    /**
     * @return whether exceptions that occur during method execution should be thrown silently or not.
     */
    boolean silentlyThrowMethodExecution();

    /**
     * @return whether bot extensions (additional plugins) should be enabled.
     */
    boolean extensionsEnabled();
}
