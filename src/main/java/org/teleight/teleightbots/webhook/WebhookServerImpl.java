package org.teleight.teleightbots.webhook;

import io.jooby.ExecutionMode;
import io.jooby.Jooby;
import io.jooby.Route;
import io.jooby.ServerOptions;
import io.jooby.jackson.JacksonModule;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class WebhookServerImpl implements WebhookServer {

    private final Jooby app;

    private boolean running;
    private final Map<String, Route.Handler> postRoutes = new ConcurrentHashMap<>();

    WebhookServerImpl(@NotNull WebhookServerConfig config) {
        ServerOptions serverOptions = new ServerOptions();
        serverOptions.setHost(config.host());
        if (config.useHttps()) {
            serverOptions.setSecurePort(config.port());
            serverOptions.setSsl(config.sslOptions());
        } else {
            serverOptions.setPort(config.port());
        }

        app = Jooby.createApp(new String[]{}, ExecutionMode.DEFAULT, Jooby::new)
                .install(new JacksonModule(ApiMethod.OBJECT_MAPPER))
                .setServerOptions(serverOptions);
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        app.start();
        running = true;
    }

    @Override
    public synchronized void addPostRoute(@NotNull String path, @NotNull Route.Handler handler) {
        postRoutes.put(path, handler);
        if (running) {
            app.post(path, handler);
        } else {
            start();
        }
    }

    @Override
    public synchronized void removePostRoute(@NotNull String path) {
        if (!running) {
            return;
        }
        postRoutes.remove(path);
        restart();
    }

    @Override
    public synchronized void restart() {
        if (!running) {
            return;
        }

        close();

        if (!postRoutes.isEmpty()) {
            start();

            // Re-add routes
            for (Map.Entry<String, Route.Handler> entry : postRoutes.entrySet()) {
                app.post(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public synchronized void close() {
        if (!running) {
            return;
        }
        app.stop();
        running = false;
    }

}
