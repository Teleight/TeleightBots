package org.teleight.teleightbots.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record EncryptedPassportElement(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty("data")
        @Nullable
        String data,

        @JsonProperty("phone_number")
        @Nullable
        String phoneNumber,

        @JsonProperty("email")
        @Nullable
        String email,

        @JsonProperty("files")
        @Nullable
        PassportFile[] files,

        @JsonProperty("front_side")
        @Nullable
        PassportFile frontSide,

        @JsonProperty("reverse_side")
        @Nullable
        PassportFile reverseSide,

        @JsonProperty("selfie")
        @Nullable
        PassportFile selfie,

        @JsonProperty("translation")
        @Nullable
        PassportFile[] translation,

        @JsonProperty("hash")
        @Nullable
        String hash
) implements ApiResult {
}
