package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InlineQueryResultCachedDocument(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "document_file_id", required = true)
        @NotNull
        String documentFileId,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "input_message_content")
        @Nullable
        InputMessageContent inputMessageContent
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String documentFileId) {
        return new InlineQueryResultCachedDocument.Builder().id(id).documentFileId(documentFileId);
    }

    @Override
    public String type() {
        return "document";
    }

}
