package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

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
    InlineQueryResultType type();

    enum InlineQueryResultType implements WrappedFieldValueProvider<InlineQueryResult> {

        CACHED_AUDIO("audio", InlineQueryResultCachedAudio.class),
        CACHED_DOCUMENT("document", InlineQueryResultCachedDocument.class),
        CACHED_GIF("gif", InlineQueryResultCachedGif.class),
        CACHED_MPEG4_GIF("mpeg4_gif", InlineQueryResultCachedMpeg4Gif.class),
        CACHED_PHOTO("photo", InlineQueryResultCachedPhoto.class),
        CACHED_STICKER("sticker", InlineQueryResultCachedSticker.class),
        CACHED_VIDEO("video", InlineQueryResultCachedVideo.class),
        CACHED_VOICE("voice", InlineQueryResultCachedVoice.class),
        ARTICLE("article", InlineQueryResultArticle.class),
        AUDIO("audio", InlineQueryResultAudio.class),
        CONTACT("contact", InlineQueryResultContact.class),
        GAME("game", InlineQueryResultGame.class),
        DOCUMENT("document", InlineQueryResultDocument.class),
        GIF("gif", InlineQueryResultGif.class),
        LOCATION("location", InlineQueryResultLocation.class),
        MPEG4_GIF("mpeg4_gif", InlineQueryResultMpeg4Gif.class),
        PHOTO("photo", InlineQueryResultPhoto.class),
        VENUE("venue", InlineQueryResultVenue.class),
        VIDEO("video", InlineQueryResultVideo.class),
        VOICE("voice", InlineQueryResultVoice.class);

        private final String fieldValue;
        private final Class<? extends InlineQueryResult> wrapperClass;

        InlineQueryResultType(String fieldValue, Class<? extends InlineQueryResult> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends InlineQueryResult> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
