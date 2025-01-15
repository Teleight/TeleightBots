package org.teleight.teleightbots.webhook;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.function.Function;

/**
 * Represents a server that handles webhook requests via POST routes.
 * <p>
 * This class defines the basic functionality for a webhook server that can
 * be started, configured with POST routes, and closed.
 * <p>
 * The server is expected to listen for incoming HTTP POST requests and invoke
 * the appropriate handlers based on the path of the request.
 */
public interface WebhookServer extends Closeable {

    /**
     * Internal factory method for creating a WebhookServer instance.
     *
     * <p>
     * This method should only be used internally and should not be accessed directly
     * by external users of the API.
     * </p>
     *
     * @param config The configuration to initialize the server with. Cannot be null.
     * @return A new instance of {@code SunWebhookServerImpl}.
     */
    @ApiStatus.Internal
    static @NotNull WebhookServer internal(@NotNull WebhookServerConfig config) {
        return new SunWebhookServerImpl(config);
    }

    /**
     * Starts the webhook server, allowing it to begin accepting incoming requests.
     *
     * <p>
     * This method should be called once the server has been properly configured.
     * It will initiate the necessary network connections and begin listening
     * for incoming HTTP requests.
     * After calling this method, the server will be ready to handle requests.
     * </p>
     */
    void start();

    /**
     * Adds a POST route to the server with a specific path and an associated handler.
     *
     * <p>
     * This method registers a POST route at the specified path.
     * When the server receives a POST request on this path, the provided handler will be invoked.
     * The handler is asynchronous and is expected to handle the HTTP request
     * asynchronously.
     * </p>
     *
     * @param path     The path of the route to add. Cannot be null or empty.
     * @param response The handler function to invoke when a POST request is received.
     *                 The function takes the body of the POST request as input and returns an HTTP response.
     *                 Cannot be null.
     */
    void addPostRoute(@NotNull String path, @NotNull Function<String, HttpResponse> response);

    /**
     * Removes a previously added POST route from the server.
     *
     * <p>
     * This method deregisters the POST route at the specified path, so that
     * the server will no longer respond to POST requests at that path.
     * </p>
     *
     * @param path The path of the route to remove. Cannot be null or empty.
     */
    void removePostRoute(@NotNull String path);

}
