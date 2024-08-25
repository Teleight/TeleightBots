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
        @JsonSubTypes.Type(value = MessageOriginChannel.class, name = "channel"),
        @JsonSubTypes.Type(value = MessageOriginChat.class, name = "chat"),
        @JsonSubTypes.Type(value = MessageOriginHiddenUser.class, name = "hidden_user"),
        @JsonSubTypes.Type(value = MessageOriginUser.class, name = "user"),
})
public sealed interface MessageOrigin extends ApiResult permits
        MessageOriginChannel,
        MessageOriginChat,
        MessageOriginHiddenUser,
        MessageOriginUser {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
