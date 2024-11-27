package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.checker.regex.qual.Regex;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.common.value.qual.MatchesRegex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethodBoolean;
import org.teleight.teleightbots.api.objects.InputFile;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetWebhook(
        @JsonProperty(value = "url", required = true)
        @NotNull
        String url,

        @JsonProperty(value = "certificate")
        @Nullable
        InputFile certificate,

        @JsonProperty(value = "ip_address")
        @Nullable
        String ipAddress,

        @JsonProperty(value = "max_connections", defaultValue = "40")
        @IntRange(from = 1, to = 100)
        int maxConnections,

        @JsonProperty(value = "allowed_updates")
        @Nullable
        String[] allowedUpdates,

        @JsonProperty(value = "drop_pending_updates")
        boolean dropPendingUpdates,

        @JsonProperty(value = "secret_token")
        @MatchesRegex("^[A-Za-z0-9_-]{1,256}$")
        @Nullable
        String secretToken
) implements MultiPartApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String url) {
        return new SetWebhook.Builder().url(url);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setWebhook";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", url);
        parameters.put("certificate", certificate);
        parameters.put("ip_address", ipAddress);
        parameters.put("max_connections", maxConnections);
        parameters.put("allowed_updates", allowedUpdates);
        parameters.put("drop_pending_updates", dropPendingUpdates);
        parameters.put("secret_token", secretToken);
        return parameters;
    }
}
