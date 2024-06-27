package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

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
    PassportElementError.PassportElementErrorType source();


    enum PassportElementErrorType implements WrappedFieldValueProvider<PassportElementError> {

        DATA_FIELD("data", PassportElementErrorDataField.class),
        FRONT_SIDE("front_side", PassportElementErrorFrontSide.class),
        REVERSE_SIDE("reverse_side", PassportElementErrorReverseSide.class),
        SELFIE("selfie", PassportElementErrorSelfie.class),
        FILE("file", PassportElementErrorFile.class),
        FILES("files", PassportElementErrorFiles.class),
        TRANSLATION_FILE("translation_file", PassportElementErrorTranslationFile.class),
        TRANSLATION_FILES("translation_files", PassportElementErrorTranslationFiles.class),
        UNSPECIFIED("unspecified", PassportElementErrorUnspecified.class);

        private final String fieldValue;
        private final Class<? extends PassportElementError> wrapperClass;

        PassportElementErrorType(String fieldValue, Class<? extends PassportElementError> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends PassportElementError> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }


}
