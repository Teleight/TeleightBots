package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface BackgroundFill extends ApiResult permits
        BackgroundFillSolid,
        BackgroundFillGradient,
        BackgroundFillFreeformGradient {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    BackgroundFill.BackgroundFillType type();

    enum BackgroundFillType implements WrappedFieldValueProvider<BackgroundFill> {

        SOLID("solid", BackgroundFillSolid.class),
        GRADIENT("gradient", BackgroundFillGradient.class),
        FREEFORM_GRADIENT("freeform_gradient", BackgroundFillFreeformGradient.class);

        private final String fieldValue;
        private final Class<? extends BackgroundFill> wrapperClass;

        BackgroundFillType(String fieldValue, Class<? extends BackgroundFill> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public Class<? extends BackgroundFill> getWrapperClass() {
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
