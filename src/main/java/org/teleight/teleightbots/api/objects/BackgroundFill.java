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
        @JsonSubTypes.Type(value = BackgroundFillSolid.class, name = "solid"),
        @JsonSubTypes.Type(value = BackgroundFillGradient.class, name = "gradient"),
        @JsonSubTypes.Type(value = BackgroundFillFreeformGradient.class, name = "freeform_gradient"),
})
public sealed interface BackgroundFill extends ApiResult permits
        BackgroundFillSolid,
        BackgroundFillGradient,
        BackgroundFillFreeformGradient {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
