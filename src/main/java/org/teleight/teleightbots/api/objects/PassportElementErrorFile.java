package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportElementErrorFile(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "file_hash", required = true)
        String fileHash,

        @JsonProperty(value = "message", required = true)
        String message
) implements PassportElementError {

    @Override
    public String source() {
        return "file";
    }

}
