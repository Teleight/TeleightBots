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
public record SendGift(
        @JsonProperty(value = "user_id", required = true)
        int userId,

        @JsonProperty(value = "chat_id")
        @Nullable
        String chatId,

        @JsonProperty(value = "gift_id", required = true)
        @NotNull
        String giftId,

        @JsonProperty(value = "pay_for_upgrade")
        boolean payForUpgrade,

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

    public static @NotNull Builder ofBuilder(int userId, String giftId) {
        return new SendGift.Builder()
                .userId(userId)
                .giftId(giftId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "sendGift";
    }

}
