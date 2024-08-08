package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface RevenueWithdrawalState extends ApiResult permits
        RevenueWithdrawalStatePending,
        RevenueWithdrawalStateSucceeded,
        RevenueWithdrawalStateFailed {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    RevenueWithdrawalStateType type();

    enum RevenueWithdrawalStateType implements WrappedFieldValueProvider<RevenueWithdrawalState> {
        PENDING("pending", RevenueWithdrawalStatePending.class),
        SUCCEEDED("succeeded", RevenueWithdrawalStateSucceeded.class),
        FAILED("failed", RevenueWithdrawalStateFailed.class);

        private final String fieldValue;
        private final Class<? extends RevenueWithdrawalState> wrapperClass;

        RevenueWithdrawalStateType(String fieldValue, Class<? extends RevenueWithdrawalState> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends RevenueWithdrawalState> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
