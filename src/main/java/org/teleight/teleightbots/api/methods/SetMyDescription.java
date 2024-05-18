package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;

public record SetMyDescription(
        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<Boolean> {

    public static Builder of() {
        return new SetMyDescription.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyDescription";
    }

    public static class Builder {
        private String description;
        private String languageCode;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public SetMyDescription build() {
            return new SetMyDescription(this.description, this.languageCode);
        }
    }
}
