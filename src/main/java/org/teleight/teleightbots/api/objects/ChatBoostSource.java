package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.teleight.teleightbots.api.ApiResult;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "source",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatBoostSourceGiftCode.class, name = "gift_code"),
        @JsonSubTypes.Type(value = ChatBoostSourceGiveaway.class, name = "giveaway"),
        @JsonSubTypes.Type(value = ChatBoostSourcePremium.class, name = "premium"),
})
public sealed interface ChatBoostSource extends ApiResult permits
        ChatBoostSourceGiftCode,
        ChatBoostSourceGiveaway,
        ChatBoostSourcePremium {

    String TYPE_NAME = "source";

    @JsonProperty(TYPE_NAME)
    String source();

}
