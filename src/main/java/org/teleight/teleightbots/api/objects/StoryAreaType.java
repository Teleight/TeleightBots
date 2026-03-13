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
        @JsonSubTypes.Type(value = StoryAreaTypeLocation.class, name = "location"),
        @JsonSubTypes.Type(value = StoryAreaTypeSuggestedReaction.class, name = "suggested_reaction"),
        @JsonSubTypes.Type(value = StoryAreaTypeLink.class, name = "link"),
        @JsonSubTypes.Type(value = StoryAreaTypeWeather.class, name = "weather"),
        @JsonSubTypes.Type(value = StoryAreaTypeUniqueGift.class, name = "unique_gift")
})
public sealed interface StoryAreaType extends ApiResult permits
        StoryAreaTypeLocation,
        StoryAreaTypeSuggestedReaction,
        StoryAreaTypeLink,
        StoryAreaTypeWeather,
        StoryAreaTypeUniqueGift {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();
}
