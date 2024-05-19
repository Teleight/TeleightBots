package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotName;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetMyName(
        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotName> {

    public static Builder ofBuilder() {
        return new GetMyName.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyName";
    }

    @Override
    public @NotNull BotName deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BotName.class);
    }

    public static class Builder {
        private String languageCode;

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public GetMyName build() {
            return new GetMyName(this.languageCode);
        }
    }
}
