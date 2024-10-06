package org.teleight.teleightbots.bot.webhook;

import org.teleight.teleightbots.api.methods.DeleteWebhook;
import org.teleight.teleightbots.bot.WebhookTelegramBot;

public interface WebhookMessageHandler {

    void onStartup(WebhookTelegramBot bot);

    default DeleteWebhook createDeleteWebhook() {
        return DeleteWebhook.ofBuilder().build();
    }

}
