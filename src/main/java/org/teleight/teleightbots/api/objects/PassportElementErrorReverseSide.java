package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportElementErrorReverseSide(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "file_hash", required = true)
        String fileHash,

        @JsonProperty(value = "message", required = true)
        String message
) implements PassportElementError {

    @Override
    public String source() {
        return "reverse_side";
    }

}
