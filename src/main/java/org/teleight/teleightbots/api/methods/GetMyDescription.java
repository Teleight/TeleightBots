package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotDescription;

public record GetMyDescription(
        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotDescription> {

    public static Builder ofBuilder() {
        return new GetMyDescription.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyDescription";
    }

    public static class Builder {
        private String languageCode;

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public GetMyDescription build() {
            return new GetMyDescription(this.languageCode);
        }
    }
}
