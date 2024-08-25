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
        @JsonSubTypes.Type(value = InputMediaPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = InputMediaVideo.class, name = "video"),
        @JsonSubTypes.Type(value = InputMediaAnimation.class, name = "animation"),
        @JsonSubTypes.Type(value = InputMediaAudio.class, name = "audio"),
        @JsonSubTypes.Type(value = InputMediaDocument.class, name = "document"),
})
public sealed interface InputMedia extends ApiResult permits
        InputMediaPhoto,
        InputMediaVideo,
        InputMediaAnimation,
        InputMediaAudio,
        InputMediaDocument {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
