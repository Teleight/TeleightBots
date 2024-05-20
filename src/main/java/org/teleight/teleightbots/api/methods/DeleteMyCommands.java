package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.BotCommandScope;

public record DeleteMyCommands(
        @JsonProperty(value = "scope")
        @Nullable
        BotCommandScope scope,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethodBoolean {

    public static Builder ofBuilder() {
        return new DeleteMyCommands.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteMyCommands";
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

        public DeleteMyCommands build() {
            return new DeleteMyCommands(this.scope, this.languageCode);
        }
    }
}
