package org.teleight.teleightbots.files;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.File;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for a File Downloader that provides methods for downloading files from a given path or a Telegram File object.
 */
public interface FileDownloader {

    /**
     * Shuts down the file downloader.
     */
    void shutdown();

    /**
     * Downloads a file from a given file path.
     *
     * @param filePath the path of the file to be downloaded
     * @return a CompletableFuture that will complete with the downloaded file
     * @throws DownloadFileException if an error occurs during the file download
     */
    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath) throws DownloadFileException;

    /**
     * Downloads a file from a given Telegram File object.
     *
     * @param telegramFile the Telegram File object representing the file to be downloaded
     * @return a CompletableFuture that will complete with the downloaded file
     * @throws DownloadFileException if an error occurs during the file download
     */
    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile) throws DownloadFileException;

    /**
     * Downloads a file from a given Telegram File object and writes it to a specified output file.
     *
     * @param telegramFile the Telegram File object representing the file to be downloaded
     * @param outputFile   the file to which the downloaded file will be written
     * @return a CompletableFuture that will complete with the downloaded file
     * @throws DownloadFileException if an error occurs during the file download
     */
    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull File telegramFile, @NotNull java.io.File outputFile) throws DownloadFileException;

    /**
     * Downloads a file from a given file path and writes it to a specified output file.
     *
     * @param filePath   the path of the file to be downloaded
     * @param outputFile the file to which the downloaded file will be written
     * @return a CompletableFuture that will complete with the downloaded file
     */
    @NotNull CompletableFuture<java.io.File> downloadFile(@NotNull String filePath, java.io.File outputFile);

}
