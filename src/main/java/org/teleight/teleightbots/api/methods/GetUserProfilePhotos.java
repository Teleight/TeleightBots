package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.UserProfilePhotos;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetUserProfilePhotos(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "offset")
        int offset,

        @JsonProperty(value = "limit")
        int limit
) implements ApiMethod<UserProfilePhotos> {

    public static @NotNull Builder ofBuilder(long userId) {
        return new GetUserProfilePhotos.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserProfilePhotos";
    }

    @Override
    public @NotNull UserProfilePhotos deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, UserProfilePhotos.class);
    }

}
