package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;

public record SetMyName(
        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethod<Boolean> {

    public static Builder of() {
        return new SetMyName.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyName";
    }

    public static class Builder {
        private String name;
        private String languageCode;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public SetMyName build() {
            return new SetMyName(this.name, this.languageCode);
        }
    }
}
