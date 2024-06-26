package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public record InputContactMessageContent(
        @JsonProperty(value = "phone_number", required = true)
        String phoneNumber,

        @JsonProperty(value = "first_name", required = true)
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "vcard")
        @Nullable
        String vcard
) implements InputMessageContent {
}
