package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.teleight.teleightbots.api.ApiResult;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "status",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransactionPartnerUser.class, name = "user"),
        @JsonSubTypes.Type(value = TransactionPartnerFragment.class, name = "fragment"),
        @JsonSubTypes.Type(value = TransactionPartnerTelegramAds.class, name = "telegram_ads"),
        @JsonSubTypes.Type(value = TransactionPartnerOther.class, name = "other"),
        @JsonSubTypes.Type(value = TransactionPartnerTelegramApi.class, name = "telegram_api")
})
public sealed interface TransactionPartner extends ApiResult permits
        TransactionPartnerUser,
        TransactionPartnerFragment,
        TransactionPartnerTelegramAds,
        TransactionPartnerOther,
        TransactionPartnerTelegramApi {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
