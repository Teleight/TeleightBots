package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.OwnedGifts;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record GetChatGifts(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "exclude_unsaved")
        boolean excludeUnsaved,

        @JsonProperty(value = "exclude_saved")
        boolean excludeSaved,

        @JsonProperty(value = "exclude_unlimited")
        boolean excludeUnlimited,

        @JsonProperty(value = "exclude_limited_upgradable")
        boolean excludeLimitedUpgradable,

        @JsonProperty(value = "exclude_limited_non_upgradable")
        boolean excludeLimitedNonUpgradable,

        @JsonProperty(value = "exclude_from_blockchain")
        boolean excludeFromBlockchain,

        @JsonProperty(value = "exclude_unique")
        boolean excludeUnique,

        @JsonProperty(value = "sort_by_price")
        boolean sortByPrice,

        @JsonProperty(value = "offset")
        String offset,

        @JsonProperty(value = "limit")
        @IntRange(from = 1, to = 100)
        int limit
) implements ApiMethod<OwnedGifts> {

    public static @NotNull Builder ofBuilder(String chatId) {
        return new GetChatGifts.Builder().chatId(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "getChatGifts";
    }

    @Override
    public @NotNull OwnedGifts deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, OwnedGifts.class);
    }

}
