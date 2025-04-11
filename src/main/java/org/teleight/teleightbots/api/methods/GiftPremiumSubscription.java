package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GiftPremiumSubscription(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "month_count", required = true)
        int monthCount,

        @JsonProperty(value = "star_count", required = true)
        int starCount,

        @JsonProperty(value = "text")
        @Nullable
        String text,

        @JsonProperty(value = "text_parse_mode")
        @Nullable
        ParseMode textParseMode,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(long userId, int monthCount, int starCount) {
        return new GiftPremiumSubscription.Builder()
                .userId(userId)
                .monthCount(monthCount)
                .starCount(starCount);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "giftPremiumSubscription";
    }
}
