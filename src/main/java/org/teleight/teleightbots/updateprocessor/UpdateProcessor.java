package org.teleight.teleightbots.updateprocessor;

import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public sealed interface UpdateProcessor<T extends TelegramBot> extends Closeable permits
        LongPollingUpdateProcessor,
        WebhookUpdateProcessor {

    CompletableFuture<User> start();

    void setBot(T bot);

}
