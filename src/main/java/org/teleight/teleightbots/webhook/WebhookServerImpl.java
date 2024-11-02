package org.teleight.teleightbots.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.ContentType;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class WebhookServerImpl implements WebhookServer {

    private final WebhookServerConfig config;

    private Javalin app;
    private volatile boolean running = false;

    private final Map<String, Handler> postRoutes = new ConcurrentHashMap<>();

    WebhookServerImpl(@NotNull WebhookServerConfig config) {
        this.config = config;
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        this.app = Javalin.create(javalinConfig -> {
            javalinConfig.useVirtualThreads = true;
            javalinConfig.showJavalinBanner = false;

            /* SSL Configuration */
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

                    conf.keystoreFromPath(keystorePath.toString(), keystorePassword);
                    conf.secure = true;
                    conf.insecure = false;
                    conf.host = config.host();
                    conf.securePort = config.port();
                    javalinConfig.bundledPlugins.enableSslRedirects();
                } else {
                    conf.insecure = true;
                    conf.secure = false;
                    conf.host = config.host();
                    conf.insecurePort = config.port();
                }
            });
            javalinConfig.registerPlugin(sslPlugin);

            javalinConfig.http.defaultContentType = ContentType.JSON;
        }).start();

        running = true;
    }

    public void addPostRoute(@NotNull String path, @NotNull Handler handler) {
        postRoutes.put(path, handler);
        if (running) {
            app.post(path, handler);
        } else {
            start();
        }
    }

    public void removePostRoute(@NotNull String path) {
        if (!running) {
            return;
        }
        postRoutes.remove(path);
        restart();
    }

    public synchronized void restart() {
        if (!running) {
            return;
        }

        close();

        if (!postRoutes.isEmpty()) {
            start();

            for (Map.Entry<String, io.javalin.http.Handler> entry : postRoutes.entrySet()) {
                app.post(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void close() {
        if (!running) {
            return;
        }
        app.stop();
        app = null;
        running = false;
    }

}
