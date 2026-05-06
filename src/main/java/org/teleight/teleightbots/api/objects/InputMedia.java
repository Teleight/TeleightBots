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

    /**
     * Creates a builder for constructing an instance of {@code InputMediaPhoto}.
     *
     * @param media the input file to be used as the media content
     * @return a builder instance for constructing {@code InputMediaPhoto}
     */
    static InputMediaPhoto.Builder photoBuilder(InputFile media) {
        return InputMediaPhoto.ofBuilder(media);
    }

    /**
     * Creates a builder for constructing an instance of {@code InputMediaVideo}.
     *
     * @param media the input file to be used as the media content
     * @return a builder instance for constructing {@code InputMediaVideo}
     */
    static InputMediaVideo.Builder videoBuilder(InputFile media) {
        return InputMediaVideo.ofBuilder(media);
    }

    /**
     * Creates a builder for constructing an instance of {@code InputMediaAnimation}.
     *
     * @param media the input file to be used as the media content; must not be null
     * @return a builder instance for constructing {@code InputMediaAnimation}
     */
    static InputMediaAnimation.Builder animationBuilder(InputFile media) {
        return InputMediaAnimation.ofBuilder(media);
    }

    /**
     * Creates a builder for constructing an instance of {@code InputMediaAudio}.
     *
     * @param media the input file to be used as the media content; must not be null
     * @return a builder instance for constructing {@code InputMediaAudio}
     */
    static InputMediaAudio.Builder audioBuilder(InputFile media) {
        return InputMediaAudio.ofBuilder(media);
    }

    /**
     * Creates a builder for constructing an instance of {@code InputMediaDocument}.
     *
     * @param media the input file to be used as the media content; must not be null
     * @return a builder instance for constructing {@code InputMediaDocument}
     */
    static InputMediaDocument.Builder documentBuilder(InputFile media) {
        return InputMediaDocument.ofBuilder(media);
    }

}
