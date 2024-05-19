package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultContact(
        @JsonProperty(value = "id", required = true)
        @NotNull
        String id,

        @JsonProperty(value = "phone_number", required = true)
        @NotNull
        String phoneNumber,

        @JsonProperty(value = "first_name", required = true)
        @NotNull
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "vcard")
        @Nullable
        String vcard,

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

    @Override
    public InlineQueryResultType type() {
        return InlineQueryResultType.INLINE_QUERY_RESULT_CONTACT;
    }

}
