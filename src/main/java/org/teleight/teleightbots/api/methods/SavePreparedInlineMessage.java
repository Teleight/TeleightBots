package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InlineQueryResult;
import org.teleight.teleightbots.api.objects.PreparedInlineMessage;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SavePreparedInlineMessage(
        @JsonProperty(value = "user_id", required = true)
        int userId,

        @JsonProperty(value = "result", required = true)
        @NotNull
        InlineQueryResult result,

        @JsonProperty(value = "allow_user_chats")
        boolean allowUserChats,

        @JsonProperty(value = "allow_bot_chats")
        boolean allowBotChats,

        @JsonProperty(value = "allow_group_chats")
        boolean allowGroupChats,

        @JsonProperty(value = "allow_channel_chats")
        boolean allowChannelChats
) implements ApiMethod<PreparedInlineMessage> {

    public static @NotNull Builder ofBuilder(int userId, InlineQueryResult result) {
        return new SavePreparedInlineMessage.Builder().userId(userId).result(result);
    }

    @Override
    public @NotNull PreparedInlineMessage deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, PreparedInlineMessage.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "savePreparedInlineMessage";
    }

}
