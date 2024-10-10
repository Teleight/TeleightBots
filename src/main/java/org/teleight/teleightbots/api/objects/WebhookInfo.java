package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record WebhookInfo(
        @JsonProperty(value = "url", required = true)
        String url,

        @JsonProperty(value = "has_custom_certificate", required = true)
        boolean hasCustomCertificate,

        @JsonProperty(value = "pending_update_count", required = true)
        int pendingUpdateCount,

        @JsonProperty(value = "ip_address")
        String ipAddress,

        @JsonProperty(value = "last_error_date")
        int lastErrorDate,

        @JsonProperty(value = "last_error_message")
        @Nullable
        String lastErrorMessage,

        @JsonProperty(value = "last_synchronization_error_date")
        int lastSynchronizationErrorDate,

        @JsonProperty(value = "max_connections")
        int maxConnections,

        @JsonProperty(value = "allowed_updates")
        @Nullable
        String[] allowedUpdates
) implements ApiResult {
}
