package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.User;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public sealed interface UpdateProcessor extends Closeable permits
        LongPollingUpdateProcessor,
        WebhookUpdateProcessor {

    @NotNull CompletableFuture<User> start();

}
