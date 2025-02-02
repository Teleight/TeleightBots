package org.teleight.teleightbots.webhook;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.FileInputStream;
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
    private volatile boolean isRunning = false;

    SunWebhookServerImpl(@NotNull WebhookServerConfig config) {
        this.config = config;
    }

    @Override
    public synchronized void start() throws Exception {
        if (isRunning) {
            throw new IllegalStateException("Server already running");
        }
        try {
            if (config.useHttps()) {
                startHttps();
            } else {
                startHttp();
            }
            isRunning = true;
        } catch (Exception e) {
            isRunning = false;
            throw e;
        }
    }

    private void startHttp() throws Exception {
        log.info("Starting internal HTTP server on {}:{}", config.host() == null ? "localhost" : config.host(), config.port());
        final InetAddress address = Inet4Address.getByName(config.host());
        server = HttpServer.create(new InetSocketAddress(address, config.port()), 0);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        log.info("HTTP server started");
    }

    private void startHttps() throws Exception {
        log.info("Starting internal HTTPS server on {}:{}", config.host() == null ? "localhost" : config.host(), config.port());
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

        final KeyStore keyStore = KeyStore.getInstance(
                keystorePath.toString().endsWith(".p12") ? "PKCS12" : "JKS"
        );
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
                    params.setNeedClientAuth(false);
                    params.setCipherSuites(new String[]{
                            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
                            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
                            "TLS_AES_256_GCM_SHA384"
                    });
                    params.setProtocols(new String[]{"TLSv1.2", "TLSv1.3"});

                    SSLParameters sslParameters = context.getSupportedSSLParameters();
                    sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
                    params.setSSLParameters(sslParameters);
                } catch (Exception e) {
                    log.error("Failed to configure HTTPS parameters: {}", e.getMessage());
                }
            }
        });

        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        log.info("HTTPS server started");
    }

    @Override
    public void addPostRoute(@NotNull String path, @NotNull Function<String, HttpResponse> response) {
        if (!isRunning) {
            throw new IllegalStateException("Server not running");
        }
        if (server == null) {
            throw new IllegalStateException("Server not initialized");
        }
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
        if (!isRunning) {
            throw new IllegalStateException("Server not running");
        }
        if (server == null) {
            throw new IllegalStateException("Server not initialized");
        }
        server.removeContext(path);
    }

    @Override
    public void close() {
        if (!isRunning) {
            return;
        }
        if (server == null) {
            throw new IllegalStateException("Server not initialized");
        }
        server.stop(0);
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

}
