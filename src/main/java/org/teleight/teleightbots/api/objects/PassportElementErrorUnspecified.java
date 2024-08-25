package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportElementErrorUnspecified(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "element_hash", required = true)
        String elementHash,

        @JsonProperty(value = "message", required = true)
        String message
) implements PassportElementError {

    @Override
    public String source() {
        return "unspecified";
    }

}
