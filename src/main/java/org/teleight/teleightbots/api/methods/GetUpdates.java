package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.List;

public record GetUpdates(
        @JsonProperty("offset")
        int offset,

        @JsonProperty("limit")
        int limit,

        @JsonProperty("timeout")
        int timeout,

        @JsonProperty("allowed_updates")
        @Nullable
        List<String> allowedUpdates
) implements ApiMethod<Update[]> {

    public static @NotNull Builder of() {
        return new Builder();
    }

    @Override
    public Update @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, Update.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUpdates";
    }

    public static final class Builder {
        private int offset;
        private int limit = BotSettings.DEFAULT.updatesTimeout();
        private int timeout = BotSettings.DEFAULT.updatesTimeout();
        private List<String> allowedUpdates;

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder allowedUpdates(@Nullable List<String> allowedUpdates) {
            this.allowedUpdates = allowedUpdates;
            return this;
        }

        public GetUpdates build() {
            return new GetUpdates(this.offset, this.limit, this.timeout, this.allowedUpdates);
        }
    }

}
