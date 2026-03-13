package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.UserProfileAudios;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetUserProfileAudios(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "offset")
        int offset,

        @JsonProperty(value = "limit")
        @IntRange(from = 1, to = 100)
        int limit
) implements ApiMethod<UserProfileAudios> {

    public static @NotNull Builder ofBuilder(long userId) {
        return new GetUserProfileAudios.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getUserProfileAudios";
    }

    @Override
    public @NotNull UserProfileAudios deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, UserProfileAudios.class);
    }

}
