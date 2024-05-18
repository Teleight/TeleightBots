package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record CallbackQuery(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "from", required = true)
        User from,

        @JsonProperty("message")
        @Nullable
        Message message,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId,

        @JsonProperty("data")
        @Nullable
        String data
) implements ApiResult {

    public static @NotNull Builder of(String id, User from) {
        return new CallbackQuery.Builder(id, from);
    }

    public static final class Builder {
        private final String id;
        private final User from;
        private Message message;
        private String inlineMessageId;
        private String data;

        public Builder(String id, User from) {
            this.id = id;
            this.from = from;
        }

        public Builder message(Message message) {
            this.message = message;
            return this;
        }

        public Builder inlineMessageId(String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public CallbackQuery build() {
            return new CallbackQuery(this.id, this.from, this.message, this.inlineMessageId, this.data);
        }
    }

}
