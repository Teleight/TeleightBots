package org.teleight.teleightbots.api.methods.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;

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

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder id(@NotNull String id);

        @NotNull Builder from(@NotNull User from);

        @NotNull Builder message(@NotNull Message message);

        @NotNull Builder inlineMessageId(@NotNull String inlineMessageId);

        @NotNull Builder data(@NotNull String data);

        @NotNull CallbackQuery build();
    }

    static final class BuilderImpl implements Builder {
        private String id;
        private User from;
        private Message message;
        private String inlineMessageId;
        private String data;

        @Override
        public @NotNull Builder id(@NotNull String id) {
            this.id = id;
            return this;
        }

        @Override
        public @NotNull Builder from(@NotNull User from) {
            this.from = from;
            return this;
        }

        @Override
        public @NotNull Builder message(@NotNull Message message) {
            this.message = message;
            return this;
        }

        @Override
        public @NotNull Builder inlineMessageId(@NotNull String inlineMessageId) {
            this.inlineMessageId = inlineMessageId;
            return this;
        }

        @Override
        public @NotNull Builder data(@NotNull String data) {
            this.data = data;
            return this;
        }

        @Override
        public @NotNull CallbackQuery build() {
            return new CallbackQuery(id,from, message, inlineMessageId, data);
        }
    }

}
