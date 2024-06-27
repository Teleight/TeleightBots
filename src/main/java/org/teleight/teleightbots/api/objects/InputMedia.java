package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface InputMedia extends ApiResult permits
        InputMediaPhoto,
        InputMediaVideo,
        InputMediaAnimation,
        InputMediaAudio,
        InputMediaDocument {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    InputMediaType type();

    enum InputMediaType implements WrappedFieldValueProvider<InputMedia> {
        PHOTO("photo", InputMediaPhoto.class),
        VIDEO("video", InputMediaVideo.class),
        ANIMATION("animation", InputMediaAnimation.class),
        AUDIO("audio", InputMediaAudio.class),
        DOCUMENT("document", InputMediaDocument.class);

        private final String fieldValue;
        private final Class<? extends InputMedia> wrapperClass;

        InputMediaType(String fieldValue, Class<? extends InputMedia> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public Class<? extends InputMedia> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }
    }

}
