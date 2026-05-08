package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.BotAccessSettings;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetManagedBotAccessSettings(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "is_access_restricted", required = true)
        boolean isAccessRestricted,

        @JsonProperty(value = "added_users")
        @Nullable
        User[] addedUsers
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId, boolean isAccessRestricted) {
        return new SetManagedBotAccessSettings.Builder()
                .userId(userId)
                .isAccessRestricted(isAccessRestricted);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setManagedBotAccessSettings";
    }

}
