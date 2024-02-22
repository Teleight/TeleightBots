package org.teleight.teleightbots.api.methods.inline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMessage;
import org.teleight.teleightbots.api.objects.LinkPreviewOptions;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.utils.ParseMode;

@Builder
public record EditMessageText(
        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "message_id")
        @Nullable
        Integer messageId,

        @JsonProperty(value = "inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty(value = "reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements ApiMethodMessage {

    @Override
    public @NotNull String getEndpointURL() {
        return "editMessageText";
    }

    public static class EditMessageTextBuilder {
        @Tolerate
        public EditMessageText.EditMessageTextBuilder chatId(@NotNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }

}
