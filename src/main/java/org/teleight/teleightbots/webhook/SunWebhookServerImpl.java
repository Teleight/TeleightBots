package org.teleight.teleightbots.webhook;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
final class SunWebhookServerImpl implements WebhookServer {

    private HttpServer server;
    private final WebhookServerConfig config;

    SunWebhookServerImpl(@NotNull WebhookServerConfig config) {
        this.config = config;
    }

    @Override
    public void start() {
        if (config.useHttps()) {
            startHttps();
        } else {
            startHttp();
        }
    }

    private void startHttp() {
        log.info("Starting HTTP server on {}:{}", config.host() == null ? "localhost" : config.host(), config.port());
        try {
            final InetAddress address = Inet4Address.getByName(config.host());
            server = HttpServer.create(new InetSocketAddress(address, config.port()), 0);
            server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            server.start();
            log.info("HTTP server started");
        } catch (IOException e) {
            log.error("Failed to start HTTP server: {}", e.getMessage());
        }
    }

    private void startHttps() {
        log.info("Starting HTTPS server on {}:{}", config.host() == null ? "localhost" : config.host(), config.port());
        try {
            final InetAddress address = Inet4Address.getByName(config.host());
            SSLContext sslContext = SSLContext.getInstance("TLS");

            final Path keystorePath = config.keystorePath();
            if (keystorePath == null) {
                throw new IllegalArgumentException("Keystore path is required for HTTPS");
            }

            final String keystorePassword = config.keystorePassword();
            if (keystorePassword == null) {
                throw new IllegalArgumentException("Keystore password is required for HTTPS");
            }

            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (FileInputStream fis = new FileInputStream(keystorePath.toFile())) {
                keyStore.load(fis, keystorePassword.toCharArray());
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keystorePassword.toCharArray());

            sslContext.init(kmf.getKeyManagers(), null, null);

            server = HttpsServer.create(new InetSocketAddress(address, config.port()), 0);
            ((HttpsServer) server).setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        SSLParameters sslParameters = context.getSupportedSSLParameters();
                        params.setSSLParameters(sslParameters);
                    } catch (Exception e) {
                        log.error("Failed to configure HTTPS parameters: {}", e.getMessage());
                    }
                }
            });

            server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            server.start();
            log.info("HTTPS server started");
        } catch (Exception e) {
            log.error("Failed to start HTTPS server: {}", e.getMessage());
        }
    }

    @Override
    public void addPostRoute(@NotNull String path, @NotNull Function<String, HttpResponse> response) {
        server.createContext(path, exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
                return;
            }
            final String body = new String(exchange.getRequestBody().readAllBytes());
            final HttpResponse httpResponse = response.apply(body);

            byte[] bodyBytes = httpResponse.body().getBytes();
            exchange.sendResponseHeaders(httpResponse.statusCode().getCode(), bodyBytes.length);
            exchange.getResponseBody().write(bodyBytes);
            exchange.getResponseBody().flush();
            exchange.close();
        });
    }

    @Override
    public void removePostRoute(@NotNull String path) {
        server.removeContext(path);
    }

    @Override
    public void close() {
        server.stop(0);
    }

}
