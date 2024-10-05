package org.teleight.teleightbots.updateprocessor.webhook;

import io.javalin.Javalin;
import io.javalin.http.ContentType;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class WebhookServer {

    private static WebhookServer instance;
    private final ReentrantLock lock = new ReentrantLock();

    private Javalin app;
    private boolean running = false;

    public void start(WebhookServerConfig config) {
        app = Javalin.create(javalinConfig -> {
            javalinConfig.useVirtualThreads = true;
            // TODO Add SSL support
            javalinConfig.http.defaultContentType = ContentType.JSON;
        }).start();

        lock.lock();
        try {
            running = true;
        } finally {
            lock.unlock();
        }
    }

    public void close() throws IOException {
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

    public Javalin getApp() {
        return app;
    }

    public static WebhookServer getInstance() {
        if (instance == null) {
            instance = new WebhookServer();
        }
        return instance;
    }


}
