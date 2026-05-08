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
        @JsonSubTypes.Type(value = InputMediaAnimation.class, name = "animation"),
        @JsonSubTypes.Type(value = InputMediaLivePhoto.class, name = "live_photo"),
        @JsonSubTypes.Type(value = InputMediaLocation.class, name = "location"),
        @JsonSubTypes.Type(value = InputMediaPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = InputMediaSticker.class, name = "sticker"),
        @JsonSubTypes.Type(value = InputMediaVenue.class, name = "venue"),
        @JsonSubTypes.Type(value = InputMediaVideo.class, name = "video"),
})
public sealed interface InputPollOptionMedia extends ApiResult permits
        InputMediaAnimation,
        InputMediaLivePhoto,
        InputMediaLocation,
        InputMediaPhoto,
        InputMediaSticker,
        InputMediaVenue,
        InputMediaVideo {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
