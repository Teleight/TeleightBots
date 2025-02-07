package org.teleight.teleightbots.bot.settings;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.InputFile;

/**
 * Settings for configuring a webhook-based Telegram bot.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * WebhookBotSettings settings = WebhookBotSettings.ofBuilder("https://your.webhook.url/", "/bot-webhook")
 *           .maxConnections(100)
 *           .allowedUpdates(new String[]{"message", "callback_query"})
 *           .dropPendingUpdates(true)
 *           .secretToken("your_secret_token")
 *           .build();
 * }</pre>
 *
 * @param url                          The full webhook URL used for setting up the bot's webhook with Telegram.
 * @param path                         The URL path on your server where the webhook will be received.
 *                                     By default, this is set to the bot's username.
 * @param certificate                  Public key certificate for the webhook.
 *                                     The certificate supplied should be PEM encoded (ASCII BASE64),
 *                                     the pem file should only contain the public key
 *                                     (including BEGIN and END portions).
 *                                     When converting from a bundle format, please split the file to only include the public key.
 * @param ipAddress                    The fixed IP address used to send webhook requests.
 * @param maxConnections               The maximum number of simultaneous HTTPS connections the bot can use.
 * @param allowedUpdates               A list of update types the bot will receive (e.g., messages, callbacks).
 * @param dropPendingUpdates           Whether pending updates should be discarded when setting up the webhook.
 * @param secretToken                  A secret token for verifying the origin of the webhook requests.
 * @param silentlyThrowMethodExecution Whether to silently throw method execution errors.
 * @param extensionsEnabled            Whether bot extensions are enabled.
 */
@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
public record WebhookBotSettings(
        String url,
        String path,
        // Public key certificate for the webhook
        InputFile certificate,
        String ipAddress,
        int maxConnections,
        String[] allowedUpdates,
        boolean dropPendingUpdates,
        String secretToken,
        boolean silentlyThrowMethodExecution,
        boolean extensionsEnabled
) implements BotSettings {

    /**
     * Creates a new {@link Builder} instance with the specified webhook URL.
     *
     * @param url  the webhook URL for registering with Telegram. Cannot be {@code null}.
     * @param path the URL path on your server where the webhook will be received. Cannot be {@code null}.
     * @return a new {@link Builder} instance pre-configured with the provided URL
     * @throws NullPointerException if the {@code url} is {@code null}
     */
    public static @NotNull Builder ofBuilder(@NotNull String url, @NotNull String path) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return new WebhookBotSettings.Builder().url(url).path(path);
    }

    /**
     * Creates a new instance of {@link WebhookBotSettings} with the specified URL.
     *
     * @param url  the webhook URL for registering with Telegram. Cannot be {@code null}.
     * @param path the URL path on your server where the webhook will be received. Cannot be {@code null}.
     * @return a new {@link WebhookBotSettings} instance
     * @throws NullPointerException if the {@code url} is {@code null}
     */
    public static @NotNull WebhookBotSettings of(@NotNull String url, @NotNull String path) {
        return ofBuilder(url, path).build();
    }

    public static class Builder {
        Builder() {
            silentlyThrowMethodExecution = true;
            extensionsEnabled = false;
        }
    }
}
