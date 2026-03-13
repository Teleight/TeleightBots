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
        @JsonSubTypes.Type(value = OwnedGiftRegular.class, name = "regular"),
        @JsonSubTypes.Type(value = OwnedGiftUnique.class, name = "unique")
})
public sealed interface OwnedGift extends ApiResult permits
        OwnedGiftRegular,
        OwnedGiftUnique {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();
}
