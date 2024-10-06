package org.teleight.teleightbots.updateprocessor.webhook;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;
import io.javalin.http.ContentType;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class WebhookServer implements Closeable {

    private static WebhookServer instance;
    private WebhookServerConfig config;
    private final ReentrantLock lock = new ReentrantLock();

    private Javalin app;
    private boolean running = false;

    private final Map<String, io.javalin.http.Handler> postRoutes = new HashMap<>();

    public void start(WebhookServerConfig config) {
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

        lock.lock();
        try {
            running = true;
        } finally {
            lock.unlock();
        }
    }

    public void addPostRoute(String path, io.javalin.http.Handler handler) {
        lock.lock();
        try {
            postRoutes.put(path, handler);
            if (running) {
                app.post(path, handler);
            } else {
                start(config);
            }
        } finally {
            lock.unlock();
        }
    }

    public void removePostRoute(String path) {
        if (running) {
            postRoutes.remove(path);
            conditionedRestart();
        }
    }

    public void conditionedRestart() {
        lock.lock();
        try {
            if (running) {
                close();

                if (!postRoutes.isEmpty()) {
                    start(config);

                    for (Map.Entry<String, io.javalin.http.Handler> entry : postRoutes.entrySet()) {
                        app.post(entry.getKey(), entry.getValue());
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        lock.lock();
        try {
            if (running) {
                app.stop();
                app = null;
                running = false;
            }
        } finally {
            lock.unlock();
        }
    }

    public static WebhookServer getInstance() {
        if (instance == null) {
            instance = new WebhookServer();
        }
        return instance;
    }


}
