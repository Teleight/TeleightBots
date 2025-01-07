package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.DeleteWebhook;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.webhook.WebhookServer;

/**
 * Represents a Telegram bot that uses webhooks to receive updates.
 */
public sealed interface WebhookTelegramBot extends TelegramBot permits WebhookTelegramBotImpl {

    @ApiStatus.Internal
    static @NotNull WebhookTelegramBot create(@NotNull String token,
                                              @NotNull String username,
                                              @NotNull WebhookBotSettings webhookSettings,
                                              @NotNull WebhookServer webhookServer) {
        return new WebhookTelegramBotImpl(token, username, webhookSettings, webhookServer);
    }

    /**
     * Gets the webhook bot settings.
     *
     * @return The webhook bot settings. Can't be null.
     */
    @NotNull WebhookBotSettings getBotSettings();

    /**
     * Gets the delete webhook for the bot.
     *
     * @return The delete webhook for the bot. Can't be null
     */
    @NotNull DeleteWebhook getDeleteWebhook();

    /**
     * Sets the webhook for the bot.
     *
     * @param deleteWebhook The webhook to set. Can't be null.
     */
    void setDeleteWebhook(@NotNull DeleteWebhook deleteWebhook);

}
