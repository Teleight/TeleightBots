package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportElementErrorDataField(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "field_name", required = true)
        String fieldName,

        @JsonProperty(value = "data_hash", required = true)
        String dataHash,

        @JsonProperty(value = "message", required = true)
        String message
) implements PassportElementError {

    @Override
    public PassportElementErrorType source() {
        return PassportElementErrorType.DATA_FIELD;
    }

}
