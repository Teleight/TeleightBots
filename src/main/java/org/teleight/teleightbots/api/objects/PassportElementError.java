package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.teleight.teleightbots.api.ApiResult;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "source",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportElementErrorDataField.class, name = "data"),
        @JsonSubTypes.Type(value = PassportElementErrorFrontSide.class, name = "front_side"),
        @JsonSubTypes.Type(value = PassportElementErrorReverseSide.class, name = "reverse_side"),
        @JsonSubTypes.Type(value = PassportElementErrorSelfie.class, name = "selfie"),
        @JsonSubTypes.Type(value = PassportElementErrorFile.class, name = "file"),
        @JsonSubTypes.Type(value = PassportElementErrorFiles.class, name = "files"),
        @JsonSubTypes.Type(value = PassportElementErrorTranslationFile.class, name = "translation_file"),
        @JsonSubTypes.Type(value = PassportElementErrorTranslationFiles.class, name = "translation_files"),
        @JsonSubTypes.Type(value = PassportElementErrorUnspecified.class, name = "unspecified"),
})
public sealed interface PassportElementError extends ApiResult permits
        PassportElementErrorDataField,
        PassportElementErrorFrontSide,
        PassportElementErrorReverseSide,
        PassportElementErrorSelfie,
        PassportElementErrorFile,
        PassportElementErrorFiles,
        PassportElementErrorTranslationFile,
        PassportElementErrorTranslationFiles,
        PassportElementErrorUnspecified {

    String TYPE_NAME = "source";

    @JsonProperty(TYPE_NAME)
    String source();

}
