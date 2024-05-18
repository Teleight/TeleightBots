package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotCommand;
import org.teleight.teleightbots.api.objects.BotCommandScope;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetMyCommands(
        @JsonProperty(value = "scope")
        @Nullable
        BotCommandScope scope,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotCommand[]> {

    public static Builder of() {
        return new GetMyCommands.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyCommands";
    }

    @Override
    public BotCommand @NotNull [] deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponseArray(answer, BotCommand[].class);
    }

    public static class Builder {
        private BotCommandScope scope;
        private String languageCode;

        public Builder scope(BotCommandScope scope) {
            this.scope = scope;
            return this;
        }

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public GetMyCommands build() {
            return new GetMyCommands(this.scope, this.languageCode);
        }
    }
}
