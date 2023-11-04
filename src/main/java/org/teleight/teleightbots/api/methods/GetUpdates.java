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

    public static @NotNull GetUpdates of() {
        return new GetUpdates(0, BotSettings.DEFAULT.updatesTimeout(), BotSettings.DEFAULT.updatesTimeout(), null);
    }

    public @NotNull GetUpdates withOffset(int offset) {
        return new GetUpdates(offset, limit, timeout, allowedUpdates);
    }

    public @NotNull GetUpdates withLimit(int limit) {
        return new GetUpdates(offset, limit, timeout, allowedUpdates);
    }

    public @NotNull GetUpdates withTimeout(int timeout) {
        return new GetUpdates(offset, limit, timeout, allowedUpdates);
    }

    public @NotNull GetUpdates withAllowedUpdates(List<String> allowedUpdates) {
        return new GetUpdates(offset, limit, timeout, allowedUpdates);
    }

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public Update @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, Update.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUpdates";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder offset(int offset);

        @NotNull Builder limit(int limit);

        @NotNull Builder timeout(int timeout);

        @NotNull Builder allowedUpdates(@Nullable List<String> allowedUpdates);

        @NotNull GetUpdates build();
    }

    static final class BuilderImpl implements Builder {
        private int offset;
        private int limit;
        private int timeout;
        private List<String> allowedUpdates;

        @Override
        public @NotNull Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        @Override
        public @NotNull Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public @NotNull Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        @Override
        public @NotNull Builder allowedUpdates(@Nullable List<String> allowedUpdates) {
            this.allowedUpdates = allowedUpdates;
            return this;
        }

        @Override
        public @NotNull GetUpdates build() {
            return new GetUpdates(offset, limit, timeout, allowedUpdates);
        }
    }

}
