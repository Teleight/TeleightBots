package org.teleight.teleightbots.updateprocessor.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.ContentType;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

public class WebhookServer implements Closeable {

    private static WebhookServer instance;
    private WebhookServerConfig config;

    private Javalin app;
    private boolean running = false;

    private final Map<String, io.javalin.http.Handler> postRoutes = new HashMap<>();

    public synchronized void start(WebhookServerConfig config) {
        this.config = config;

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

    public synchronized void addPostRoute(String path, io.javalin.http.Handler handler) {
        postRoutes.put(path, handler);
        if (running) {
            app.post(path, handler);
        } else {
            start(config);
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
            start(config);

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

    public static synchronized WebhookServer getInstance() {
        if (instance == null) {
            instance = new WebhookServer();
        }
        return instance;
    }


}
