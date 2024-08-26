package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
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

    public static @NotNull Builder ofBuilder(String id, String phoneNumber, String firstName) {
        return new InlineQueryResultContact.Builder().id(id).phoneNumber(phoneNumber).firstName(firstName);
    }

    @Override
    public String type() {
        return "contact";
    }

}
