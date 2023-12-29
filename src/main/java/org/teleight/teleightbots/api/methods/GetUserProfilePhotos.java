package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.UserProfilePhotos;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder
public record GetUserProfilePhotos(
        @JsonProperty(value = "user_id", required = true)
        @NotNull
        Integer userId,

        @JsonProperty(value = "offset")
        @Nullable
        Integer offset,

        @JsonProperty(value = "limit")
        @Nullable
        Integer limit
) implements ApiMethod<UserProfilePhotos> {
    @Override
    public @NotNull UserProfilePhotos deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, UserProfilePhotos.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserProfilePhotos";
    }
}
