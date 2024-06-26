package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

public record SetMyShortDescription(
        @JsonProperty(value = "short_description")
        @Nullable
        String shortDescription,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethodBoolean {

    public static Builder ofBuilder() {
        return new SetMyShortDescription.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyShortDescription";
    }

    public static class Builder {
        private String shortDescription;
        private String languageCode;

        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public SetMyShortDescription build() {
            return new SetMyShortDescription(this.shortDescription, this.languageCode);
        }
    }
}
