package org.teleight.teleightbots.files;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.File;

import java.util.concurrent.CompletableFuture;

public interface FileDownloader {

    void shutdown();

    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath) throws DownloadFileException;

    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile) throws DownloadFileException;

    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile, @NotNull java.io.File outputFile) throws DownloadFileException;

    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath, java.io.File outputFile);

}
