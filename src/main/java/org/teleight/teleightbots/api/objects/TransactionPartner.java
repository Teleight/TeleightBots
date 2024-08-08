package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface TransactionPartner extends ApiResult permits
        TransactionPartnerUser,
        TransactionPartnerFragment,
        TransactionPartnerTelegramAds,
        TransactionPartnerOther {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    TransactionPartnerType type();

    enum TransactionPartnerType implements WrappedFieldValueProvider<TransactionPartner> {
        USER("user", TransactionPartnerUser.class),
        FRAGMENT("fragment", TransactionPartnerFragment.class),
        TELEGRAM_ADS("telegram_ads", TransactionPartnerTelegramAds.class),
        OTHER("other", TransactionPartnerOther.class);

        private final String fieldValue;
        private final Class<? extends TransactionPartner> wrapperClass;

        TransactionPartnerType(String fieldValue, Class<? extends TransactionPartner> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends TransactionPartner> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
