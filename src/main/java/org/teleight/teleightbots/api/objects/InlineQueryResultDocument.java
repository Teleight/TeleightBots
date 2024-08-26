package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultDocument(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

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

        @JsonProperty(value = "document_url", required = true)
        @NotNull
        String documentUrl,

        @JsonProperty(value = "mime_type", required = true)
        @NotNull
        String mimeType,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent,

        @JsonProperty(value = "thumbnail_url")
        @Nullable
        String thumbnailUrl,

        @JsonProperty(value = "thumbnail_width")
        int thumbnailWidth,

        @JsonProperty(value = "thumbnail_height")
        int thumbnailHeight
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String title, String documentUrl, String mimeType) {
        return new InlineQueryResultDocument.Builder().id(id).title(title).documentUrl(documentUrl).mimeType(mimeType);
    }

    @Override
    public String type() {
        return "document";
    }

}
