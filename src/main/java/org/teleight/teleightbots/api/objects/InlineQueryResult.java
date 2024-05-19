package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

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

    enum InlineQueryResultType {
        INLINE_QUERY_RESULT_CACHED_AUDIO("audio", InlineQueryResultCachedAudio.class),
        INLINE_QUERY_RESULT_CACHED_DOCUMENT("document", InlineQueryResultCachedDocument.class),
        INLINE_QUERY_RESULT_CACHED_GIF("gif", InlineQueryResultCachedGif.class),
        INLINE_QUERY_RESULT_CACHED_MPEG4_GIF("mpeg4_gif", InlineQueryResultCachedMpeg4Gif.class),
        INLINE_QUERY_RESULT_CACHED_PHOTO("photo", InlineQueryResultCachedPhoto.class),
        INLINE_QUERY_RESULT_CACHED_STICKER("sticker", InlineQueryResultCachedSticker.class),
        INLINE_QUERY_RESULT_CACHED_VIDEO("video", InlineQueryResultCachedVideo.class),
        INLINE_QUERY_RESULT_CACHED_VOICE("voice", InlineQueryResultCachedVoice.class),
        INLINE_QUERY_RESULT_ARTICLE("article", InlineQueryResultArticle.class),
        INLINE_QUERY_RESULT_AUDIO("audio", InlineQueryResultAudio.class),
        INLINE_QUERY_RESULT_CONTACT("contact", InlineQueryResultContact.class),
        INLINE_QUERY_RESULT_GAME("game", InlineQueryResultGame.class),
        INLINE_QUERY_RESULT_DOCUMENT("document", InlineQueryResultDocument.class),
        INLINE_QUERY_RESULT_GIF("gif", InlineQueryResultGif.class),
        INLINE_QUERY_RESULT_LOCATION("location", InlineQueryResultLocation.class),
        INLINE_QUERY_RESULT_MPEG4_GIF("mpeg4_gif", InlineQueryResultMpeg4Gif.class),
        INLINE_QUERY_RESULT_PHOTO("photo", InlineQueryResultPhoto.class),
        INLINE_QUERY_RESULT_VENUE("venue", InlineQueryResultVenue.class),
        INLINE_QUERY_RESULT_VIDEO("video", InlineQueryResultVideo.class),
        INLINE_QUERY_RESULT_VOICE("voice", InlineQueryResultVoice.class);

        private final String fieldValue;
        private final Class<? extends InlineQueryResult> wrapperClass;

        InlineQueryResultType(String fieldValue, Class<? extends InlineQueryResult> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public Class<? extends InlineQueryResult> getWrapperClass() {
            return wrapperClass;
        }

        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
