package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.PassportElementError;

public record SetPassportDataErrors(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "errors", required = true)
        @NotNull
        PassportElementError[] errors
) implements ApiMethodBoolean {

    public static SetPassportDataErrors.Builder ofBuilder(long userId, PassportElementError[] errors) {
        return new SetPassportDataErrors.Builder(userId, errors);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setPassportDataErrors";
    }

    public static class Builder {
        private final long userId;
        private final PassportElementError[] errors;

        Builder(long userId, PassportElementError[] errors) {
            this.userId = userId;
            this.errors = errors;
        }

        public SetPassportDataErrors build() {
            return new SetPassportDataErrors(this.userId, this.errors);
        }
    }

}
