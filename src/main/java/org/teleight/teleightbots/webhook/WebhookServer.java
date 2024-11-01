package org.teleight.teleightbots.webhook;

import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * Represents a webhook server.
 * <p>
 * The Webhook server is used to receive incoming updates from Telegram.
 */
public sealed interface WebhookServer extends Closeable permits WebhookServerImpl {

    static @NotNull WebhookServer create(@NotNull WebhookServerConfig config) {
        return new WebhookServerImpl(config);
    }

    /**
     * Starts the webserver.
     */
    void start();

    /**
     * Adds a POST route to the server.
     *
     * @param path The path of the route. Can't be null.
     * @param handler The handler of the route. Can't be null.
     */
    void addPostRoute(@NotNull String path, @NotNull Handler handler);

    /**
     * Removes a POST route from the server.
     *
     * @param path The path of the route. Can't be null.
     */
    void removePostRoute(@NotNull String path);

    /**
     * Restarts the server.
     */
    void restart();

}
