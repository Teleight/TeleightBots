package org.teleight.teleightbots.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record EncryptedCredentials(
        @JsonProperty(value = "data", required = true)
        String data,

        @JsonProperty(value = "hash", required = true)
        String hash,

        @JsonProperty(value = "secret", required = true)
        String secret
) implements ApiResult {
}
