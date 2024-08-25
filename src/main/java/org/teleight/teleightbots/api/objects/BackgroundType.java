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
        @JsonSubTypes.Type(value = BackgroundTypeFill.class, name = "fill"),
        @JsonSubTypes.Type(value = BackgroundTypeWallpaper.class, name = "wallpaper"),
        @JsonSubTypes.Type(value = BackgroundTypePattern.class, name = "pattern"),
        @JsonSubTypes.Type(value = BackgroundTypeChatTheme.class, name = "chat_theme"),
})
public sealed interface BackgroundType extends ApiResult permits
        BackgroundTypeFill,
        BackgroundTypeWallpaper,
        BackgroundTypePattern,
        BackgroundTypeChatTheme {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();

}
