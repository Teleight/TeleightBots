package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface PaidMedia extends ApiResult permits
        PaidMediaPreview,
        PaidMediaPhoto,
        PaidMediaVideo {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    PaidMediaType type();

    enum PaidMediaType implements WrappedFieldValueProvider<PaidMedia> {

        PREVIEW("preview", PaidMediaPreview.class),
        PHOTO("photo", PaidMediaPhoto.class),
        VIDEO("video", PaidMediaVideo.class);

        private final String fieldValue;
        private final Class<? extends PaidMedia> wrapperClass;

        PaidMediaType(String fieldValue, Class<? extends PaidMedia> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends PaidMedia> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
