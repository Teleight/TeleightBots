package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.BotShortDescription;

public record GetMyShortDescription(
        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<BotShortDescription> {

    public static Builder of() {
        return new GetMyShortDescription.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getMyShortDescription";
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
