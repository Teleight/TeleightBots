package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record ReplyParameters(
        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "allow_sending_without_reply")
        boolean allowSendingWithoutReply,

        @JsonProperty(value = "quote")
        @Nullable
        String quote,

        @JsonProperty(value = "quote_parse_mode")
        @Nullable
        ParseMode quoteParseMode,

        @JsonProperty(value = "quote_entities")
        @Nullable
        MessageEntity[] quoteEntities,

        @JsonProperty(value = "quote_position")
        int quotePosition
) implements ApiResult {

    public static Builder of(int messageId) {
        return new ReplyParameters.Builder(messageId);
    }

    public static class Builder {
        private final int messageId;
        private String chatId;
        private boolean allowSendingWithoutReply;
        private String quote;
        private ParseMode quoteParseMode;
        private MessageEntity[] quoteEntities;
        private int quotePosition;

        public Builder(int messageId) {
            this.messageId = messageId;
        }

        public Builder chatId(String chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder allowSendingWithoutReply(boolean allowSendingWithoutReply) {
            this.allowSendingWithoutReply = allowSendingWithoutReply;
            return this;
        }

        public Builder quote(@Nullable String quote) {
            this.quote = quote;
            return this;
        }

        public Builder quoteParseMode(@Nullable ParseMode quoteParseMode) {
            this.quoteParseMode = quoteParseMode;
            return this;
        }

        public Builder quoteEntities(@Nullable MessageEntity[] quoteEntities) {
            this.quoteEntities = quoteEntities;
            return this;
        }

        public Builder quotePosition(int quotePosition) {
            this.quotePosition = quotePosition;
            return this;
        }

        public ReplyParameters build() {
            return new ReplyParameters(this.messageId, this.chatId, this.allowSendingWithoutReply, this.quote, this.quoteParseMode, this.quoteEntities, this.quotePosition);
        }
    }

}
