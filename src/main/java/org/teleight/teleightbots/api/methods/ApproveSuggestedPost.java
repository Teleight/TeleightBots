package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.util.Date;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record ApproveSuggestedPost(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "send_date")
        @Nullable
        Date sendDate
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String chatId, int messageId) {
        return new ApproveSuggestedPost.Builder().chatId(chatId).messageId(messageId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "approveSuggestedPost";
    }

}
