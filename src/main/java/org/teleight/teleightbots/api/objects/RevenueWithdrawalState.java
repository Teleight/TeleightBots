package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.teleight.teleightbots.api.ApiResult;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RevenueWithdrawalStatePending.class, name = "pending"),
        @JsonSubTypes.Type(value = RevenueWithdrawalStateSucceeded.class, name = "succeeded"),
        @JsonSubTypes.Type(value = RevenueWithdrawalStateFailed.class, name = "failed"),
})
public sealed interface RevenueWithdrawalState extends ApiResult permits
        RevenueWithdrawalStatePending,
        RevenueWithdrawalStateSucceeded,
        RevenueWithdrawalStateFailed {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
