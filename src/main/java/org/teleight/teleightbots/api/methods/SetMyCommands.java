package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.BotCommand;
import org.teleight.teleightbots.api.objects.BotCommandScope;

import java.util.List;

public record SetMyCommands(
        @JsonProperty(value = "commands", required = true)
        @NotNull
        List<BotCommand> commands,

        @JsonProperty(value = "scope")
        @Nullable
        BotCommandScope scope,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethodBoolean {

    public static Builder ofBuilder(List<BotCommand> commands) {
        return new SetMyCommands.Builder(commands);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyCommands";
    }

    public static class Builder {
        private final List<BotCommand> commands;
        private BotCommandScope scope;
        private String languageCode;

        Builder(List<BotCommand> commands) {
            this.commands = commands;
        }

        public Builder scope(@Nullable BotCommandScope scope) {
            this.scope = scope;
            return this;
        }

        public Builder languageCode(@Nullable String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public SetMyCommands build() {
            return new SetMyCommands(this.commands, this.scope, this.languageCode);
        }
    }
}
