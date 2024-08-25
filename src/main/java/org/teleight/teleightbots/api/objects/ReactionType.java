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
        @JsonSubTypes.Type(value = ReactionTypeEmoji.class, name = "emoji"),
        @JsonSubTypes.Type(value = ReactionTypeCustomEmoji.class, name = "custom_emoji"),
        @JsonSubTypes.Type(value = ReactionTypePaid.class, name = "paid"),
})
public sealed interface ReactionType extends ApiResult permits
        ReactionTypeEmoji,
        ReactionTypeCustomEmoji,
        ReactionTypePaid {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();
}
