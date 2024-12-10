package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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
        boolean hideUrl,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "thumbnail_url")
        @Nullable
        String thumbnailUrl,

        @JsonProperty(value = "thumbnail_width")
        int thumbnailWidth,

        @JsonProperty(value = "thumbnail_height")
        int thumbnailHeight
) implements InlineQueryResult {

    public static @NotNull Builder ofBuilder(String id, String title, InputMessageContent inputMessageContent) {
        return new InlineQueryResultArticle.Builder()
                .id(id)
                .title(title)
                .inputMessageContent(inputMessageContent);
    }

    @Override
    public String type() {
        return "article";
    }

}
