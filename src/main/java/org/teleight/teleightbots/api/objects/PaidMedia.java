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
        @JsonSubTypes.Type(value = PaidMediaPreview.class, name = "preview"),
        @JsonSubTypes.Type(value = PaidMediaPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = PaidMediaVideo.class, name = "video"),
})
public sealed interface PaidMedia extends ApiResult permits
        PaidMediaPreview,
        PaidMediaPhoto,
        PaidMediaVideo {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
