package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultVideo(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "video_url", required = true)
        @NotNull
        String videoUrl,

        @JsonProperty(value = "mime_type", required = true)
        @NotNull
        String mimeType,

        @JsonProperty(value = "thumbnail_url", required = true)
        @NotNull
        String thumbnailUrl,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty(value = "video_width")
        int videoWidth,

        @JsonProperty(value = "video_height")
        int videoHeight,

        @JsonProperty(value = "video_duration")
        int videoDuration,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String videoUrl, String mimeType, String thumbnailUrl, String title) {
        return new InlineQueryResultVideo.Builder()
                .id(id)
                .videoUrl(videoUrl)
                .mimeType(mimeType)
                .thumbnailUrl(thumbnailUrl)
                .title(title);
    }

    @Override
    public String type() {
        return "video";
    }

}
