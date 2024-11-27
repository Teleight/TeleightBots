package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.util.Date;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetUserEmojiStatus(
        @JsonProperty(value = "user_id", required = true)
        int userId,

        @JsonProperty(value = "emoji_status_custom_emoji_id")
        String emojiStatusCustomEmojiId,

        @JsonProperty(value = "emoji_status_expiration_date")
        Date emojiStatusExpirationDate
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(int userId) {
        return new SetUserEmojiStatus.Builder().userId(userId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setUserEmojiStatus";
    }

}
