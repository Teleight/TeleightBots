package org.teleight.teleightbots.updateprocessor;

import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public sealed interface UpdateProcessor extends Closeable permits
        LongPollingUpdateProcessor,
        WebhookUpdateProcessor {

    CompletableFuture<User> start();

    void setBot(TelegramBot bot);

}
