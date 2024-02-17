package org.teleight.teleightbots.files;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.File;
import org.teleight.teleightbots.bot.Bot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

public class FileDownloaderImpl implements FileDownloader {

    private final Bot bot;

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
            .version(HttpClient.Version.HTTP_2)
            .build();

    public FileDownloaderImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void shutdown() {
        System.out.println("Shutting down FileDownloader");
        client.close();
    }

    @Override
    public final @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath) throws DownloadFileException {
        final String tempFileName = Long.toString(System.currentTimeMillis());
        final java.io.File tempFile = createTempFile(tempFileName);
        return downloadFile(filePath, tempFile);
    }

    @Override
    public final @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile) throws DownloadFileException {
        final java.io.File tempFile = createTempFile(telegramFile.fileId());
        return downloadFile(telegramFile, tempFile);
    }

    @Override
    public final @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile, @NotNull java.io.File outputFile) throws DownloadFileException {
        final String filePath = telegramFile.filePath();
        if (filePath == null) {
            throw new DownloadFileException("Unable to download file. File path is null");
        }
        final String url = getFileUrl(filePath);
        return downloadToFile(url, outputFile);
    }

    @Override
    public final @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath, java.io.File outputFile) {
        final String url = getFileUrl(filePath);
        return downloadToFile(url, outputFile);
    }

    private @NotNull CompletableFuture<java.io.File> downloadToFile(@NotNull String url, @NotNull java.io.File output) {
        return UNSAFE_executeFileDownload(url, output);
    }

    @ApiStatus.Internal
    private @NotNull CompletableFuture<java.io.File> UNSAFE_executeFileDownload(@NotNull String url, java.io.@NotNull File output) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
                final HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(output.toPath()));
                final int statusCode = response.statusCode();
                if (statusCode == 200) {
                    return response.body().toFile();
                } else {
                    throw new DownloadFileException("Unexpected Status code while downloading file. Expected 200 got " + statusCode);
                }
            } catch (IOException | DownloadFileException e) {
                throw new DownloadFileException("Error downloading file", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new DownloadFileException("Error downloading file", e);
            }
        });
    }

    private @NotNull java.io.File createTempFile(String tempFileName) throws RuntimeException {
        try {
            return java.io.File.createTempFile(tempFileName, ".tmp");
        } catch (IOException e) {
            throw new DownloadFileException("Error downloading file", e);
        }
    }

    private @NotNull String getFileUrl(@NotNull String filePath) {
        return String.format("https://api.telegram.org/file/bot%s/%s", bot.getBotToken(), filePath);
    }

}
