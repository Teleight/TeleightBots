package org.teleight.teleightbots.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.ContentType;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

final class WebhookServerImpl implements WebhookServer {

    private final WebhookServerConfig config;

    private Javalin app;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Map<String, Handler> postRoutes = new ConcurrentHashMap<>();

    public WebhookServerImpl(WebhookServerConfig config) {
        this.config = config;
    }

    @Override
    public void start() {
        if (running.getAndSet(true)) {
            return;
        }

        this.app = Javalin.create(javalinConfig -> {
            javalinConfig.useVirtualThreads = true;
            configureSsl(javalinConfig);
            javalinConfig.http.defaultContentType = ContentType.JSON;
        }).start(config.host(), config.port());

        postRoutes.forEach(app::post);
    }

    private void configureSsl(JavalinConfig javalinConfig) {
        SslPlugin sslPlugin = new SslPlugin(conf -> {
            if (config.useHttps()) {

                final Path keystorePath = config.keystorePath();
                if (keystorePath == null) {
                    throw new IllegalArgumentException("Keystore path is required for HTTPS");
                }

                final String keystorePassword = config.keystorePassword();
                if (keystorePassword == null) {
                    throw new IllegalArgumentException("Keystore password is required for HTTPS");
                }

                conf.keystoreFromPath(config.keystorePath().toString(), config.keystorePassword());
                conf.secure = true;
                conf.insecure = false;
                javalinConfig.bundledPlugins.enableSslRedirects();
            } else {
                conf.insecure = true;
                conf.secure = false;
            }
        });
        javalinConfig.registerPlugin(sslPlugin);
    }

    @Override
    public void addPostRoute(@NotNull String path, @NotNull Handler handler) {
        postRoutes.put(path, handler);
        if (running.get()) {
            app.post(path, handler);
        } else {
            start();
        }
    }

    @Override
    public void removePostRoute(@NotNull String path) {
        if (!running.get()) {
            return;
        }
        postRoutes.remove(path);
        restart();
    }

    @Override
    public synchronized void restart() {
        if (!running.get()) {
            return;
        }
        close();
    }

    @Override
    public void close() {
        if (!running.getAndSet(false)) {
            return;
        }
        app.stop();
        app = null;
    }

}
