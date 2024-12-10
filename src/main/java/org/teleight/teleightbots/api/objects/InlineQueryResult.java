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
        @JsonSubTypes.Type(value = InlineQueryResultArticle.class),
        @JsonSubTypes.Type(value = InlineQueryResultAudio.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedAudio.class),
        @JsonSubTypes.Type(value = InlineQueryResultContact.class),
        @JsonSubTypes.Type(value = InlineQueryResultDocument.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedDocument.class),
        @JsonSubTypes.Type(value = InlineQueryResultGame.class),
        @JsonSubTypes.Type(value = InlineQueryResultGif.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedGif.class),
        @JsonSubTypes.Type(value = InlineQueryResultLocation.class),
        @JsonSubTypes.Type(value = InlineQueryResultMpeg4Gif.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedMpeg4Gif.class),
        @JsonSubTypes.Type(value = InlineQueryResultPhoto.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedPhoto.class),
        @JsonSubTypes.Type(value = InlineQueryResultVenue.class),
        @JsonSubTypes.Type(value = InlineQueryResultVideo.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedVideo.class),
        @JsonSubTypes.Type(value = InlineQueryResultVoice.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedVoice.class),
        @JsonSubTypes.Type(value = InlineQueryResultCachedSticker.class),
})
public sealed interface InlineQueryResult extends ApiResult permits
        InlineQueryResultArticle,
        InlineQueryResultAudio,
        InlineQueryResultCachedAudio,
        InlineQueryResultCachedDocument,
        InlineQueryResultCachedGif,
        InlineQueryResultCachedMpeg4Gif,
        InlineQueryResultCachedPhoto,
        InlineQueryResultCachedSticker,
        InlineQueryResultCachedVideo,
        InlineQueryResultCachedVoice,
        InlineQueryResultContact,
        InlineQueryResultDocument,
        InlineQueryResultGame,
        InlineQueryResultGif,
        InlineQueryResultLocation,
        InlineQueryResultMpeg4Gif,
        InlineQueryResultPhoto,
        InlineQueryResultVenue,
        InlineQueryResultVideo,
        InlineQueryResultVoice {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    String type();
}
