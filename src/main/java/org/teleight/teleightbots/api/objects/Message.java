package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;

public record Message(
        @JsonProperty(value = "message_id", required = true)
        Integer messageId,

        @JsonProperty(value = "chat", required = true)
        Chat chat,

        @JsonProperty("text")
        String text,

        @JsonProperty("reply_markup")
        InlineKeyboardMarkup replyKeyboard

        //todo continue
) implements ApiResult {

}
