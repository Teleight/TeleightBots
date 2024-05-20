package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.UserProfilePhotos;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record GetUserProfilePhotos(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "offset")
        int offset,

        @JsonProperty(value = "limit")
        int limit
) implements ApiMethod<UserProfilePhotos> {

    public static Builder ofBuilder(long userId) {
        return new GetUserProfilePhotos.Builder(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserProfilePhotos";
    }

    @Override
    public @NotNull UserProfilePhotos deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, UserProfilePhotos.class);
    }

    public static class Builder {
        private final long userId;
        private int offset;
        private int limit;

        Builder(long userId) {
            this.userId = userId;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public GetUserProfilePhotos build() {
            return new GetUserProfilePhotos(this.userId, this.offset, this.limit);
        }
    }
}
