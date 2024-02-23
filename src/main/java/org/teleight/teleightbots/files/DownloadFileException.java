package org.teleight.teleightbots.files;

/**
 * Class representing a download file exception.
 * <p>
 * It is thrown when a file cannot be downloaded.
 */
public final class DownloadFileException extends RuntimeException {

    /**
     * Constructs a DownloadFileException with the specified message.
     *
     * @param message the detail message, saved for later retrieval by the getMessage() method
     */
    public DownloadFileException(String message) {
        super(message);
    }

    /**
     * Constructs a DownloadFileException with the specified message and cause.
     *
     * @param message the detail message, saved for later retrieval by the getMessage() method
     * @param cause   the cause of the exception
     */
    public DownloadFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
