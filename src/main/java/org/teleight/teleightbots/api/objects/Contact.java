package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Contact(
        @JsonProperty(value = "phone_number", required = true)
        String phoneNumber,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "user_id")
        long userId,

        @JsonProperty(value = "vcard")
        @Nullable
        String vcard
) implements ApiResult {
}
