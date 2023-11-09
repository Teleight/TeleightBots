package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

public record Message(
        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("from")
        User from,

        @JsonProperty("chat")
        Chat chat,

        @JsonProperty("text")
        String text,

        @JsonProperty("reply_markup")
        InlineKeyboardMarkup replyKeyboard
) implements ApiResult {

}
