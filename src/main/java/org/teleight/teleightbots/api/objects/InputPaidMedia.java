package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface InputPaidMedia extends ApiResult permits
        InputPaidMediaPhoto,
        InputPaidMediaVideo {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    InputPaidMediaType type();

    enum InputPaidMediaType implements WrappedFieldValueProvider<InputPaidMedia> {

        PHOTO("photo", InputPaidMediaPhoto.class),
        VIDEO("video", InputPaidMediaVideo.class);

        private final String fieldValue;
        private final Class<? extends InputPaidMedia> wrapperClass;

        InputPaidMediaType(String fieldValue, Class<? extends InputPaidMedia> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends InputPaidMedia> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
