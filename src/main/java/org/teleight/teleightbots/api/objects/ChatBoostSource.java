package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface ChatBoostSource extends ApiResult permits
        ChatBoostSourceGiftCode,
        ChatBoostSourceGiveaway,
        ChatBoostSourcePremium {

    String TYPE_NAME = "source";

    @JsonProperty(TYPE_NAME)
    ChatBoostSourceType source();

    enum ChatBoostSourceType implements WrappedFieldValueProvider<ChatBoostSource> {

        GIFT_CODE("gift_code", ChatBoostSourceGiftCode.class),
        GIVEAWAY("giveaway", ChatBoostSourceGiveaway.class),
        PREMIUM("premium", ChatBoostSourcePremium.class);

        private final String fieldValue;
        private final Class<? extends ChatBoostSource> wrapperClass;

        ChatBoostSourceType(String fieldValue, Class<? extends ChatBoostSource> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends ChatBoostSource> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
