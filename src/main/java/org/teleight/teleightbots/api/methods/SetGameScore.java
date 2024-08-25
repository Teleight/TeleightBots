package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodMultiResponse;
import org.teleight.teleightbots.api.objects.Message;

import java.io.Serializable;
import java.util.List;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetGameScore(
        @JsonProperty(value = "user_id", required = true)
        long userId,

        @JsonProperty(value = "score", required = true)
        @IntRange(from = 0)
        int score,

        @JsonProperty("force")
        boolean force,

        @JsonProperty("disable_edit_message")
        boolean disableEditMessage,

        @JsonProperty("chat_id")
        @Nullable
        String chatId,

        @JsonProperty("message_id")
        int messageId,

        @JsonProperty("inline_message_id")
        @Nullable
        String inlineMessageId
) implements ApiMethodMultiResponse {

    public static @NotNull Builder ofBuilder(long userId, @IntRange(from = 0) int score) {
        return new SetGameScore.Builder().userId(userId).score(score);
    }

    @Override
    public @NotNull List<Class<? extends Serializable>> getSerializableClasses() {
        return List.of(Message.class, Boolean.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setGameScore";
    }

}
