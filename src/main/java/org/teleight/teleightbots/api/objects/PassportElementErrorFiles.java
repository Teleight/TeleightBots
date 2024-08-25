package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportElementErrorFiles(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "file_hashes", required = true)
        String[] fileHashes,

        @JsonProperty(value = "message", required = true)
        String message
) implements PassportElementError {

    @Override
    public String source() {
        return "files";
    }

}
