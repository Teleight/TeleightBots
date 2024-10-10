package org.teleight.teleightbots.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.ContentType;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebhookServer implements Closeable {

    private final WebhookServerConfig config;

    private Javalin app;
    private boolean running = false;

    private final Map<String, io.javalin.http.Handler> postRoutes = new ConcurrentHashMap<>();

    public WebhookServer(WebhookServerConfig config) {
        this.config = config;
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        this.app = Javalin.create(javalinConfig -> {
            javalinConfig.useVirtualThreads = true;

            /* SSL Configuration */
            SslPlugin sslPlugin = new SslPlugin(conf -> {
                if (config.useHttps()) {
                    conf.keystoreFromPath(config.keystorePath().toString(), config.keystorePassword());
                    conf.secure = true;
                    conf.insecure = false;
                    conf.securePort = config.port();
                    javalinConfig.bundledPlugins.enableSslRedirects();
                } else {
                    conf.insecure = true;
                    conf.secure = false;
                    conf.insecurePort = config.port();
                }
            });
            javalinConfig.registerPlugin(sslPlugin);

            javalinConfig.http.defaultContentType = ContentType.JSON;
        }).start();

        running = true;
    }

    public void addPostRoute(String path, io.javalin.http.Handler handler) {
        postRoutes.put(path, handler);
        if (running) {
            app.post(path, handler);
        } else {
            start();
        }
    }

    public void removePostRoute(String path) {
        if (!running) {
            return;
        }
        postRoutes.remove(path);
        restart();
    }

    private synchronized void restart() {
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
    public synchronized void close() {
        if (!running) {
            return;
        }
        app.stop();
        app = null;
        running = false;
    }


}
