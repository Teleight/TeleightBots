package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record EncryptedPassportElement(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "data")
        @Nullable
        String data,

        @JsonProperty(value = "phone_number")
        @Nullable
        String phoneNumber,

        @JsonProperty(value = "email")
        @Nullable
        String email,

        @JsonProperty(value = "files")
        @Nullable
        PassportFile[] files,

        @JsonProperty(value = "front_side")
        @Nullable
        PassportFile frontSide,

        @JsonProperty(value = "reverse_side")
        @Nullable
        PassportFile reverseSide,

        @JsonProperty(value = "selfie")
        @Nullable
        PassportFile selfie,

        @JsonProperty(value = "translation")
        @Nullable
        PassportFile[] translation,

        @JsonProperty(value = "hash")
        @Nullable
        String hash
) implements ApiResult {
}
