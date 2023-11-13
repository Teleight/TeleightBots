package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Contact(
        @JsonProperty(value = "phone_number", required = true)
        String phoneNumber,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty("last_name")
        @Nullable
        String lastName,

        @JsonProperty("user_id")
        @Nullable
        Long userId,

        @JsonProperty("vcard")
        @Nullable
        String vcard
) implements ApiResult {
}
