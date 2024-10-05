package org.teleight.teleightbots.bot.webhook;

import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.DeleteWebhook;
import org.teleight.teleightbots.api.objects.Update;

public interface WebhookMessageHandler {

    ApiMethod<?> consumeUpdate(Update update);

    default DeleteWebhook createDeleteWebhook() {
        return DeleteWebhook.ofBuilder().build();
    }

}
