package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultArticle(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "title", required = true)
        @NotNull
        String title,

        @JsonProperty(value = "input_message_content", required = true)
        @NotNull
        InputMessageContent inputMessageContent,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup,

        @JsonProperty(value = "url")
        @Nullable
        String url,

        @JsonProperty(value = "hide_url")
        @Nullable
        Boolean hideUrl,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "thumbnail_url")
        @Nullable
        String thumbnailUrl,

        @JsonProperty(value = "thumbnail_width")
        @Nullable
        Integer thumbnailWidth,

        @JsonProperty(value = "thumbnail_height")
        @Nullable
        Integer thumbnailHeight
) implements InlineQueryResult {

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.INLINE_QUERY_RESULT_ARTICLE;
    }

}
