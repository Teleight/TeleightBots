package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.bot.webhook.WebhookMessageHandler;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

public final class WebhookTelegramBot extends BaseTelegramBot<WebhookBotSettings> {

    private final WebhookMessageHandler webhookHandler;

    public WebhookTelegramBot(@NotNull String token,
                              @NotNull String username,
                              @NotNull UpdateProcessor<WebhookTelegramBot> updateProcessor,
                              @NotNull WebhookBotSettings botSettings,
                              @NotNull WebhookMessageHandler webhookHandler) {
        super(token, username, updateProcessor, botSettings);
        this.webhookHandler = webhookHandler;
    }

    public @NotNull WebhookMessageHandler getWebhookBotInfo() {
        return webhookHandler;
    }

    @Override
    public void shutdown() {
        execute(this.webhookHandler.createDeleteWebhook());
        super.shutdown();
    }

}
