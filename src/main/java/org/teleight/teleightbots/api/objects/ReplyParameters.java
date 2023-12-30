package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Tolerate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;

@Builder
public record ReplyParameters(
        @JsonProperty(value = "message_id", required = true)
        @NotNull
        Integer messageId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "allow_sending_without_reply")
        @Nullable
        Boolean allowSendingWithoutReply,

        @JsonProperty(value = "quote")
        @Nullable
        String quote,

        @JsonProperty(value = "quote_parse_mode")
        @Nullable
        String quoteParseMode,

        @JsonProperty(value = "quote_entities")
        @Nullable
        MessageEntity[] quoteEntities,

        @JsonProperty(value = "quote_position")
        @Nullable
        Integer quotePosition
) implements ApiResult {

    public static class ReplyParametersBuilder {
        @Tolerate
        public ReplyParametersBuilder chatId(Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }

}
