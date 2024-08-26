package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultCachedMpeg4Gif(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "mpeg4_file_id", required = true)
        @NotNull
        String mpeg4FileId,

        @JsonProperty(value = "title")
        @Nullable
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

        @JsonProperty("show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String mpeg4FileId) {
        return new InlineQueryResultCachedMpeg4Gif.Builder().id(id).mpeg4FileId(mpeg4FileId);
    }

    @Override
    public String type() {
        return "mpeg4_gif";
    }

}
