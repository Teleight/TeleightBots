package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotShortDescription;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetMyShortDescription(
        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotShortDescription> {

    public static Builder ofBuilder() {
        return new GetMyShortDescription.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyShortDescription";
    }

    @Override
    public @NotNull BotShortDescription deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, BotShortDescription.class);
    }

    public static class Builder {
        private String languageCode;

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public GetMyShortDescription build() {
            return new GetMyShortDescription(this.languageCode);
        }
    }
}
